{-# LANGUAGE
  ExistentialQuantification, 
  NamedFieldPuns, 
  MultiParamTypeClasses,
  ScopedTypeVariables 
  #-}

module Graph where

import Prelude hiding (id)
import qualified Data.Map as Map
import qualified Data.Set as Set

type ID = Int

data Label l = Label l deriving (Eq, Show)

{---------------------------------- Vertex-------------------------------------}
data Eq v => Vertex v = Vertex {
      id          :: ID
    , labelVertex :: Label v } deriving (Show)

instance Eq v => Eq (Vertex v) where
    v1 == v2 = if id v1 == id v2 && 
                  labelVertex v1 == labelVertex v2 
               then True
               else False

instance Eq v => Ord (Vertex v) where
    compare v1 v2 = compare (id v1) (id v2)

{---------------------------------- Edge --------------------------------------}
data Edge e = Edge {
      origin    :: ID
    , target    :: ID
    , labelEdge :: Label e } deriving (Show)

instance Eq e => Eq (Edge e) where
    e1 == e2 = if ((origin  e1 == origin    e2 && target e1 == target e2) ||
                   (origin  e1 == target    e2 && target e1 == origin e2)) &&
                  labelEdge e1 == labelEdge e2
               then True
               else False

instance Eq e => Ord (Edge e) where


{---------------------------------- Graph -------------------------------------}
data Graph v e = Graph {
      vertices :: Set.Set (Vertex v)
    , edges    :: Set.Set (Edge e) } deriving (Show)

mkGraph :: Set.Set (Vertex v) -> Set.Set (Edge e) -> Graph v e
mkGraph vs es = Graph {vertices = vs, edges = es}
mkGraph' :: (Eq e, Eq v) => [Vertex v] -> [Edge e] -> Graph v e
mkGraph' vs es = mkGraph (Set.fromList vs) (Set.fromList es)