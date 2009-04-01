module Algorithms.Kohonen where

import Data.Array
import Data.List
import Data.Tuple
import System.IO.Unsafe (unsafePerformIO)
import System.Random
import Control.Monad
import Control.Monad.State


type Row = Integer
type Col = Integer


newtype Weights a = Weights [a] deriving (Eq, Show)

instance Functor Weights where
    fmap f (Weights ws) = Weights (map f ws)

apply :: ([a] -> [a]) -> Weights a -> Weights a
apply f (Weights ws) = Weights (f ws)


instance Random a =>  Random (Weights a) where
    random g = let (g',ws) = mapAccumL (\g x -> (snd . next $ g, fst . random $ g)) g [1..]
               in (Weights ws, g')
    randomR = error "Instance randomR for Random (Weights a) is undefined"


type WeightsField a = Array (Row,Col) (Weights a)


initWeightsField :: Random a => (Row,Col) -> Integer -> IO (WeightsField a)
initWeightsField bnds@(rs,cs) wlength = do
  g <- getStdGen
  let (g',ws') = mapAccumL 
                 (\g _ -> (snd . next $ g, 
                           mkWeight (fromIntegral wlength) g)) 
                 g 
                 [1..rs*cs]
  ws <- sequence ws'
  return $ listArray ((1,1),bnds) ws

      where mkWeight s g = do let (ws, g') = random g
                              setStdGen g'
                              return $ apply (take s) ws
