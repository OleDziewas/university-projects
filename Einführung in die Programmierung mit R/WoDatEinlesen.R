rm(list=ls())
pfad <- 'c:/Uni/Daten'
Wo <- 40
DatenMitPfad <- file.path(pfad,paste("KW_",Wo,".dat",sep=""))
if (file.exists(DatenMitPfad)) {
  WochDaten <- read.table(DatenMitPfad, sep="\t", header = TRUE)
}
