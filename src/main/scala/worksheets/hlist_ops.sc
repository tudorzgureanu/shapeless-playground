import shapeless.ops.hlist._
import shapeless._


Init[Int :: Int :: String :: HNil].apply(3 :: 4 :: "abcde" :: HNil)
Last[Int :: Int :: String :: HNil].apply(3 :: 4 :: "abcde" :: HNil)

ToList[Int :: Int :: Int :: HNil, Int].apply(3 :: 4 :: 5 :: HNil)

