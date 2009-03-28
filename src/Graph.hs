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

module Graph where

import Prelude hiding (id)
import Data.List

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
    where vs' = snd $ mapAccumL (\acc v -> (acc + 1,Vertex acc v)) 1 vs
          es' = map (\(o,t,l) -> Edge o t l) es

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



{---------------------------------- Testing -----------------------------------}
g1 = mkGraph' vs es
    where vs = [Label 'A', Label 'B', Label 'C']
          es = [(1,2,None),(2,3,None)]
g2 = mkGraph' vs es
    where vs = [Label 'X', Label 'Y', Label 'Z']
          es = [(1,2,None),(2,3,None)]
