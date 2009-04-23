{-# LANGUAGE
  ExistentialQuantification ,
  FlexibleContexts          ,
  GADTs                     ,
  MultiParamTypeClasses     ,
  NamedFieldPuns            ,
  ParallelListComp          ,
  ScopedTypeVariables       ,
  UndecidableInstances
  #-}

module Data.Graph.LabeledGraph where

import Prelude ()

import Control.Monad
import Data.Bool
import Data.Eq
import Data.Int
import Data.List.Stream
import Data.Maybe
import Data.Tuple
import System.IO
import Text.Show
import Data.Function hiding (id)

{---------------------------- Type declarations -------------------------------}
type ID = Int

data Label l = Label l | None deriving (Eq, Show)


{---------------------------------- Vertex ------------------------------------}
data Vertex v = Vertex {
      id ::ID
    ,labelV :: Label v
    } deriving (Eq, Show)


{---------------------------------- Edge --------------------------------------}
--data Edge e = Edge (ID,ID) e deriving Show
data Edge e = Edge {
      origin :: ID
    , target :: ID
    , labelE :: Label e
    } deriving Show

instance Eq (Edge e) where
    e1 == e2 = if (origin e1 == origin e2 && target e1 == target e2) ||
                  (origin e1 == target e2 && target e1 == origin e2)
               then True
               else False


{---------------------------------- Graph -------------------------------------}
data Graph v e = Graph {
      vertices :: [Vertex v]
    , edges    :: [Edge e]
    } deriving Show

mkGraph :: [Vertex v] -> [Edge e] -> Graph v e
mkGraph vs es = Graph vs es

mkGraph' :: [Label v] -> [(ID,ID,Label e)] -> Graph v e
mkGraph' vs es = mkGraph vs' es'
    where vs' = [ Vertex id v | id        <- [1.. ] | v <- vs]
          es' = [ Edge o t l  | (o, t, l) <- es              ]

connect :: Edge e -> Graph v e -> Maybe (Graph v e)
connect e g = if any (\v -> id v == origin e) (vertices g) &&
                 any (\v -> id v == target e) (vertices g)
              then Just (addEdge e g)
              else Nothing

disconnect :: Edge e -> Graph v e -> Maybe (Graph v e)
disconnect e g = if e `elem` (edges g)
                 then Just (removeEdge e g)
                 else Nothing

addEdge :: Edge e -> Graph v e -> Graph v e
addEdge e g = Graph {
                vertices = vertices g
              , edges = e : edges g }

addEdges :: [Edge e] -> Graph v e -> Graph v e
addEdges es g = foldl (\g e -> addEdge e g) g es

removeEdge :: Edge e -> Graph v e -> Graph v e
removeEdge e g = Graph (vertices g) (filter (\e1 -> e /= e1) (edges g))

removeEdges :: [Edge e] -> Graph v e -> Graph v e
removeEdges es g = foldl (\g e -> removeEdge e g) g es

connectionExists :: ID -> ID -> [Edge e] -> Bool
connectionExists o t es = any match es || any tmatch es
    where match e  = o == origin e && t == target e
          tmatch e = o == target e && t == origin e


connectionExists' :: Edge e -> Graph v e -> Bool
connectionExists' e g = connectionExists (origin e) (target e) (edges g)

connectionExists'' :: ID -> ID -> Graph v e -> Bool
connectionExists'' o t g = connectionExists o t (edges g)

connectionExists''' :: Edge e -> [Edge e] -> Bool
connectionExists''' (Edge o t _) es = connectionExists o t es

addVertex :: Vertex v -> Graph v e -> Graph v e
addVertex v g = Graph {
                  vertices = v : vertices g
                , edges = edges g }

identityEdges :: Graph v e -> [Edge e]
identityEdges g = map (\v -> Edge (id v) (id v) None) (vertices g)

addIdentityEdges :: Graph v e -> Graph v e
addIdentityEdges g = addEdges (identityEdges g) g

removeIdentityEdges :: Graph v e -> Graph v e
removeIdentityEdges g = removeEdges (identityEdges g) g

removeDuplicateEdges :: [Edge e] -> [Edge e]
removeDuplicateEdges es = foldl check [] es
    where check es e = if not $ connectionExists''' e es then e : es else es



(<>) :: (Eq v, Eq v1) =>
        Graph v e -> Graph v1 e1 -> Graph (Vertex v, Vertex v1) e2

g1 <> g2 = let g1' = g1 --addIdentityEdges g1
               g2' = g2 --addIdentityEdges g2
               vs = [ Vertex id (Label (v1, v2)) |
                      v1 <- vertices g1',
                      v2 <- vertices g2'          |
                      id <- [1..] ]

               es = removeDuplicateEdges
                    [ Edge (id v1) (id v2) None  |

                      v1 <- vs,
                      v2 <- vs,
                      v1 /= v2,

                      (     exist v1 g1'  &&      exist v2 g2') ||
                      (not (exist v1 g1') && not (exist v2 g2')) ]

                   where exist v g = connectionExists'' (nestedO v) (nestedT v) g

                         nested (Vertex _ (Label l)) = l
                         nestedO = id . fst . nested
                         nestedT = id . snd . nested

           in mkGraph vs es

modularProduct :: (Eq v, Eq v1) =>
                  Graph v e -> Graph v1 e1 -> Graph (Vertex v, Vertex v1) e2
modularProduct = (<>)



{-------------------------------- Testing & misc -------------------------------}
g1 = mkGraph' vs es
    where vs = [Label 'A', Label 'B', Label 'C']
          es = [(1,2,None),(2,3,None)]
g2 = mkGraph' vs es
    where vs = [Label 'X', Label 'Y', Label 'Z']
          es = [(1,2,None),(2,3,None)]

pprint :: Show a => [a] -> IO ()
pprint = mapM_ (putStrLn . show)

