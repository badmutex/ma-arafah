{-# LANGUAGE
  NoMonomorphismRestriction
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

(|>) :: a -> (a -> b) -> b
d |> f = f d

newtype Vector a = Vector [a] deriving (Eq,Show)

instance Functor Vector where
    fmap f (Vector ws) = Vector (fmap f ws)

instance Applicative Vector where
    pure w = Vector (repeat w)
    (Vector fs) <*> (Vector ws) = Vector (zipWith ($) fs ws)

instance MTRandom a => MTRandom (Vector a) where
    random g =  do rs <- randoms g
                   return (Vector rs)

apply :: ([a] -> [b]) -> Vector a -> Vector b
apply f (Vector ws) = Vector (f ws)

type Weights a = Vector a
type Input a = Vector a
type Field a = Array Position (Weights a)

weights_field :: MVar (IORef (Field Double))
weights_field = unsafePerformIO $ do newEmptyMVar

readFieldIORef = readMVar weights_field
takeFieldIORef = takeMVar weights_field
writeFieldIORef = putMVar weights_field
takeField = takeFieldIORef >>= readIORef
readField = readFieldIORef >>= readIORef
writeField f = takeFieldIORef >>= (\ref -> writeIORef ref f)
modifyField f = takeFieldIORef >>= (\ref -> modifyIORef ref f)

initField :: (Integral vlength) => Position -> vlength -> IO (Field Double)
initField pos@(nrows,ncols) vlength = do
  ws <- sequence . replicate (fromIntegral (nrows * ncols))
        $ return . apply (take (fromIntegral vlength)) 
            =<< randomIO
  let f = listArray ((1,1),pos) ws
  field <- newIORef f
  putMVar weights_field field
  takeField >>= return

euclidean (Vector v1) (Vector v2) = sqrt . sum $
                                    zipWith (\ a b -> (a-b)**2) v1 v2
distance = euclidean

-- TODO implement shuffle :: [a] -> [a]
shuffle :: [a] -> [a]
shuffle = undefined


find_best_match input = do
  field <- takeField
  
  let !best = assocs field
             |> minimumBy (\(_,v1) (_,v2) -> compare (d v1) (d v2))
             where d = distance input

  writeField field
  return best


neighborhood v1 v2 = 1 / (d + 1)
    where d = euclidean (Vector v1) (Vector v2)
