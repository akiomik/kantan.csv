package kantan.csv.laws

import kantan.csv
import kantan.csv.CsvResult

trait SafeRowCodecLaws[A] extends SafeRowDecoderLaws[A] with RowEncoderLaws[A] {
  def codec: csv.RowCodec[A]
  override def rowDecoder: csv.RowDecoder[A] = codec
  override def rowEncoder: csv.RowEncoder[A] = codec

  def roundTrip(a: A): Boolean = codec.decode(codec.encode(a)) == CsvResult(a)
}

object SafeRowCodecLaws {
  def apply[A](implicit c: csv.RowCodec[A]): SafeRowCodecLaws[A] = new SafeRowCodecLaws[A] {
    override implicit val codec = c
  }
}