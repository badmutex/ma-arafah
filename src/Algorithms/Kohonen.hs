module Algorithms.Kohonen where

import Control.Applicative
import Data.Array.IArray
import Data.List
import Data.Tuple
import System.IO.Unsafe (unsafePerformIO)
import System.Random.Mersenne
import Control.Monad
import Control.Monad.State


type Row = Integer
type Col = Integer


newtype Weights a = Weights [a] deriving (Eq, Show)

instance Functor Weights where
    fmap f (Weights ws) = Weights (map f ws)


apply :: ([a] -> [a]) -> Weights a -> Weights a
apply f (Weights ws) = Weights (f ws)


instance MTRandom a =>  MTRandom (Weights a) where
    random g =  do rs <- randoms g
                   return (Weights rs)




type WeightsField a = (Array (Row,Col) (Weights a)) 




initWeightsField :: (Integral b, MTRandom a) => 
                    (Row, Col) -> b -> IO (WeightsField a)
initWeightsField bnds@(rs,cs) wlength = do 
  ws <- sequence . replicate (fromIntegral (rs*cs))
        $ return . apply (take (fromIntegral wlength)) 
            =<< (randomIO :: MTRandom a => IO (Weights a))
  return $ listArray ((1,1),bnds) ws

-- test
test_wf :: WeightsField Test_MD
test_wf = unsafePerformIO $ initWeightsField (100,100) 9

data Test_MD = Test deriving Show
instance MTRandom Test_MD where
    random _ = return Test
-- /test

type InputVector a = Array Int a


