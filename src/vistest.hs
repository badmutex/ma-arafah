module Test where

import Control.Monad (replicateM)
import Data.KMeans
import Data.Traversable
import Hubigraph
import Prelude hiding (mapM, sequence)
import System.IO.Unsafe (unsafePerformIO)


datafile = "/Users/badi/src/ma-arafah.git/src/breast-cancer-wisconsin.data"


bdata = readFile datafile >>= return . map (\l -> read ("[" ++ l ++ "]")) . lines

k = unsafePerformIO $ bdata >>= return . kmeans 11


vis = do

  let ccenters = map center k

  -- vertices for cluster centers
  vs_c <- forM k (\_ -> do vid <- newVertex
                           vertexColor vid "#ff0000"
                           vertexSize vid 3
                           vertexShape vid Icosahedron
                           return vid
                 )

  -- connect cluster centers together
  let vid_centers = zip vs_c ccenters
  es_c <- mapM (\(vid_a,c_a) ->
                    forM vid_centers (\(vid_b,c_b) ->
                                   if vid_a /= vid_b
                                   then do eid <- newEdge (vid_a, vid_b)
                                           edgeSpline eid True
                                           edgeStrength eid (realToFrac
                                                             $ euclidean c_a c_b)
                                           edgeVisible eid False
                                           return eid
                                   else return (-1)
                              ) >>= return . filter (> 0))
          vid_centers


  -- vertices for data points
  vs_d <- mapM (\c -> forM c (\_ -> do vid <- newVertex
                                       vertexColor vid "#33ff00"
                                       return vid
                             )) k

  -- connect data points to their cluster center
  mapM (\(vid_c, vids) -> forM vids (\vid_d ->
                                       if vid_c /= vid_d
                                       then do eid <- newEdge (vid_c,vid_d)
                                               edgeSpline eid False
                                               edgeVisible eid False
                                               return eid
                                       else return (-1)
                                    ) >>= return . filter (> 0))
           (zip vs_c vs_d)

  
  
  return ()



center points = map (\a -> a / (fromIntegral $ length points)) 
                $ foldl1 (zipWith (+)) points

euclidean xs ys =  sqrt . sum $ zipWith (\x y -> (x - y)^2) xs ys

clear' = h >>= runHubigraph clear
serv = "http://127.0.0.1:20738/RPC2"
h = initHubigraph serv

main = initHubigraph serv >>= runHubigraph vis
