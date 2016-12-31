import shapeless.Witness
import shapeless.labelled.FieldType
import shapeless.syntax.singleton._

val field = "name" ->> "John Doe"

def getFieldName[K, T](value: FieldType[K, T])(implicit witness: Witness.Aux[K]): K =
 witness.value

def getValue[K, T](value: FieldType[K, T]): T = value

getFieldName(field)
getValue(field)


