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

field :: MVar (IORef (Field Int))
field = unsafePerformIO $ do newEmptyMVar

readFieldIORef = readMVar field
takeFieldIORef = takeMVar field
writeFieldIORef = putMVar field
takeField = takeFieldIORef >>= readIORef
readField = readFieldIORef >>= readIORef
writeField f = takeFieldIORef >>= writeIORef f
modifyField f = takeFieldIORef >>= (\ref -> modifyIORef ref f)

initField :: (Integral b) => Position -> b -> IO ()
initField pos@(nrows,ncols) vlength = do
  ws <- sequence . replicate (fromIntegral (nrows * ncols))
        $ return . apply (take (fromIntegral vlength)) 
            =<< randomIO
  let f = listArray ((1,1),pos) ws
  field' <- newIORef f
  putMVar field field'
  
foo = unsafePerformIO $ do
        initField (1,1) 3
        field <- readField
        return field


euclidean (Vector v1) (Vector v2) = sqrt . sum $ 
                                    zipWith (\ a b -> (a-b)**2) v1 v2
distance = euclidean


shuffle :: [a] -> [a]
shuffle = undefined



  