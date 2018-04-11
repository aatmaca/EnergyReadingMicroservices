# EnergyReadingMicroservices
PowerTrack Data Integration Services


1- Lists the names of the power plants given in the
service, the corresponding meter ID and store the list as a comma separated
value formatted text in “powerplants.csv” file.

2- Polls the most recent minute-based energy generation
readings from all the power plants every minute and stores this to a csv file, a
column for each power plant, a column for the timestamp as UNIX seconds
and a column for human-readable time formatted as string.
