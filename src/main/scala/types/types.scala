package types
import participante._
import participante.dragon._
import torneo.postas.Posta

object types {
  type Vikingos = List[Vikingo]
  type Dragones = List[Dragon]
  type Participantes = List[Participante]
  type Condicion = Participante => Boolean
  type Condiciones = List[Condicion]
  type Orden = (Participante, Participante) => Boolean
  type Postas = List[Posta]
  type Jinetes = List[Jinete]
  type CondicionDragon = Dragon => Boolean


}