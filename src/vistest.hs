module Test where

import Control.Monad (replicateM)
import Data.KMeans
import Data.Traversable
import Hubigraph
import Prelude hiding (mapM, sequence)
import System.IO.Unsafe (unsafePerformIO)
import Text.Printf


cancerData = "/Users/badi/src/ma-arafah.git/src/breast-cancer-wisconsin.data"
k_cancer = 11
yeastData = "/Users/badi/src/ma-arafah.git/src/yeast.data"
k_yeast = 10
o4_results = "/Users/badi/src/ma-arafah.git/src/o4-results.data"
k_o4 = 5


cancer :: IO [[Double]]
cancer = readFile cancerData
         >>= return
         . map (\l -> read ("[" ++ l ++ "]")) . lines

yeast :: IO [[Double]]
yeast = readFile yeastData
        >>= return
        . map (map read . words) . lines

o4 :: IO [[Double]]
o4 = readFile o4_results
     >>= return
     . map (map read . drop 2 . words)
     . lines

inputVectors = o4
k = unsafePerformIO $ inputVectors >>= return . kmeans k_o4


vis = do

  let ccenters = map center k

  -- vertices for cluster centers
  vs_c <- forM k (\c -> do vid <- newVertex
                           vertexColor vid "#ff0000"
--                            vertexSize  vid 0.25
                           vertexShape vid Icosahedron
                           vertexLabel vid (show . length $ c)
                           return vid
                 )

  -- connect cluster centers together
  let vid_centers = zip vs_c ccenters
  es_c <- mapM (\(vid_a,c_a) ->
                    forM vid_centers (\(vid_b,c_b) ->
                                   if vid_a /= vid_b
                                   then do eid <- newEdge (vid_a, vid_b)
                                           let d = 
                                                   realToFrac
--                                                    . round
--                                                    . (/1000)
                                                   $ euclidean c_a c_b
                                           edgeSpline     eid True
--                                            edgeStrength   eid d
                                           edgeLabel      eid (printf "%.2f" (d::Double))
                                           edgeStroke     eid Dashed
                                           edgeShowstrain eid True
                                           edgeVisible    eid True
                                           return eid
                                   else return (-1)
                              ) >>= return . filter (> 0))
          vid_centers


  -- vertices for data points
  vs_d <- mapM (\c -> forM c (\_ -> do vid <- newVertex
                                       vertexColor vid "#33ff00"
                                       vertexVisible vid True
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

rerun = clear' >> main
