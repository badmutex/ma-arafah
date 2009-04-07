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
import System.Random.Shuffle

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

weights_field :: MVar (Field Double)
weights_field = unsafePerformIO $ do newEmptyMVar


takeField = takeMVar weights_field
writeField = putMVar weights_field
modifyField f = modifyMVar_ weights_field (\field -> return $ f field)

best_matching_unit :: MVar (Vector a)
best_matching_unit = unsafePerformIO $ do newEmptyMVar

takeBMU = takeMVar best_matching_unit
writeBMU = putMVar best_matching_unit
modifyBMU f = modifyMVar_ best_matching_unit (\bmu -> return $ f bmu)

learning_restraint :: MVar Double
learning_restraint = unsafePerformIO $ do newMVar 1.0
inc_learning_restraint = modifyMVar_ learning_restraint
                         (\lr -> return $ lr * 0.1)

initField :: (Integral vlength) => Position -> vlength -> IO (Field Double)
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
