# Informe de proves - RA5 ACT2

## Dades

- Alumne: Derek Perez
- Projecte: RA5 - ACT2 (Gestio de col.leccions i documents XML amb Java i eXist-db)
- Data:

## Resultat de proves

### a) Crear una nova subcol.leccio anomenada PLAYLISTS

- Accio executada:
- Resultat esperat: crear la subcol.leccio PLAYLISTS dins /db/SPOTYDAM
- Resultat obtingut:
- Estat: PENDENT
- Evidencia:

### b) Crear i eliminar una subcol.leccio anomenada PROVA

- Accio executada:
- Resultat esperat: crear PROVA i despres eliminar-la correctament
- Resultat obtingut:
- Estat: PENDENT
- Evidencia:

### c) Intentar eliminar una subcol.leccio anomenada NOEXISTEIX

- Accio executada:
- Resultat esperat: error controlat informant que no existeix
- Resultat obtingut:
- Estat: PENDENT
- Evidencia:

### d) Afegir el document hits2026.xml a la subcol.leccio PLAYLISTS

- Accio executada:
- Resultat esperat: document desat a /db/SPOTYDAM/PLAYLISTS
- Resultat obtingut:
- Estat: PENDENT
- Evidencia:

### e) Eliminar el document hits2026.xml de la subcol.leccio PLAYLISTS

- Accio executada:
- Resultat esperat: eliminacio correcta del document
- Resultat obtingut:
- Estat: PENDENT
- Evidencia:

### f) Intentar eliminar el document noexisteix.xml de la subcol.leccio PLAYLISTS

- Accio executada:
- Resultat esperat: error controlat informant que el document no existeix
- Resultat obtingut:
- Estat: PENDENT
- Evidencia:

### g) Intentar eliminar el document noexisteix.xml de la subcol.leccio NOEXISTEIX

- Accio executada:
- Resultat esperat: error controlat informant que la subcol.leccio no existeix
- Resultat obtingut:
- Estat: PENDENT
- Evidencia:

## Observacions finals

- La implementacio s'ha fet mantenint l'estructura original del codi base.
- El fitxer modificat de codi es Main.java.
