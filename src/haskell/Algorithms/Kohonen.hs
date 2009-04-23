{-# LANGUAGE
  NoMonomorphismRestriction,
  TypeSynonymInstances
  #-}

import Data.Array.IArray
import Data.List
import Data.Tuple
import System.IO.Unsafe (unsafePerformIO)
import System.Random.Mersenne
import Control.Monad
import Control.Monad.State
import Data.IORef
import Control.Concurrent.MVar
import Control.Applicative
-- import System.Random.Shuffle

type Row = Integer
type Col = Integer
type Position = (Row,Col)

(|>) :: (a -> b) -> (b -> c) -> a -> c
(|>) = flip (.)

newtype Vector a = Vector [a] deriving (Eq,Show)

instance Functor Vector where
    fmap f (Vector ws) = Vector (fmap f ws)

instance Applicative Vector where
    pure w = Vector (repeat w)
    (Vector fs) <*> (Vector ws) = Vector (zipWith ($) fs ws)

instance MTRandom a => MTRandom (Vector a) where
    random g =  do rs <- randoms g
                   return (Vector rs)

vector_op f (Vector v1) (Vector v2) = Vector (zipWith f v1 v2)
(|+|) = vector_op (+)
(|-|) = vector_op (-)
(/*/) s = fmap (s*)


apply :: ([a] -> [b]) -> Vector a -> Vector b
apply f (Vector ws) = Vector (f ws)

type Weights a = Vector a
type Input a = Vector a
type Field a = Array Position (Weights a)


initField :: (Integral vlength, MTRandom a) => Position -> vlength -> IO (Field a)
initField pos@(nrows,ncols) vlength = do
  ws <- sequence . replicate (fromIntegral (nrows * ncols))
        $ return . apply (take (fromIntegral vlength)) 
            =<< randomIO
  let field = listArray ((1,1),pos) ws
  return field

euclidean (Vector v1) (Vector v2) = sqrt . sum $
                                    zipWith (\ a b -> (a-b)**2) v1 v2
distance = euclidean

-- TODO implement shuffle :: [a] -> [a]
shuffle :: [a] -> [a]
shuffle = undefined


find_best_match :: (Floating a, Ord a) =>
                   Field a -> Input a -> (Position, Weights a)
find_best_match field input = best
    where !best = minimumBy (\(_,v1) (_,v2) -> compare (d v1) (d v2)) 
                  $ assocs field
              where d = distance input



neighborhood v1 v2 = 1 / (d + 1)
    where d = euclidean v1 v2

update_weights :: (Floating a, Num a) =>
                  Field a -> Input a -> Weights a -> a -> Field a
update_weights field input bmu restraint = field // mods
    where mods = map update . assocs $ field
          update (pos,ws) = (pos,ws')
              where ws' = ws |+| ((n ws bmu) /*/ (restraint /*/ (input |-| ws)))
                    n = neighborhood


sfoldl :: (a -> b -> a) -> (a -> Bool) -> a -> [b] -> a
sfoldl _ _ o [] = o
sfoldl f s o (x:xs)
    | s o       = o
    | otherwise = sfoldl f s (f o x) xs


data SOMParams a = Params {
      field     :: Field a
    , restraint :: Double
    , iteration :: Integer
    , stop      :: Bool
    }

--som :: [Input a] -> SOMParams a -> Field a
som inputs params =
    field $ sfoldl (\params pattern -> project pattern params)
          stop
          params
          (cycle . shuffle $ inputs)


--project :: Input a -> SOMParams a -> SOMParams a
project pattern state = let wfield = field state
                            (pos,bmu)  = find_best_match wfield pattern
                            wfield'    = update_weights
                                         wfield
                                         pattern
                                         bmu
                                         (restraint state)
                            restraint' = restraint state * 0.1
                        in state { field     = wfield'
                                 , restraint = restraint'
                                 , iteration = iteration state + 1 }

