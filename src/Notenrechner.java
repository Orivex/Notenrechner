import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Notenrechner {
    public static void main(String[] args) {
        Notenrechner notenrechner = new Notenrechner();
        notenrechner.mach(System.getProperty("user.dir"));
    }

    void mach(String dateiWeg) {

        List<Double> noten = new ArrayList<>();

        try{
            File notenTxt = new File(dateiWeg + "/noten.txt");
            BufferedReader reader = new BufferedReader(new FileReader(notenTxt));

            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {

                int i = 0;
                while (line.charAt(i) != ' ')
                    i++;

                i++;

                Double note = Double.valueOf(line.substring(i, i + 2));
                noten.add(note);
                i+=2;
                if(i == line.length()-1 && line.charAt(i) == 'x') {
                    noten.add(note);
                }

                line = reader.readLine();
            }

            double summe_notenpunkte = 0;
            double summe_noten = 0;
            for (double note : noten) {
                summe_notenpunkte+=note;
                summe_noten += (17-note)/3;
            }

            ZonedDateTime dateTime = java.time.ZonedDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String now = dateTime.format(dateTimeFormatter);

            File statistikTxt = new File(dateiWeg + "/statistik.txt");
            PrintWriter printWriter = new PrintWriter(statistikTxt);
            BufferedWriter writer = new BufferedWriter(printWriter);

            printWriter.print("");
            writer.append("Leistungskurse werden zweifach gezählt");
            writer.newLine();
            writer.newLine();
            writer.append("Notenpunkte insgesamt: " + String.format("%.0f", summe_notenpunkte));
            writer.newLine();
            writer.append("Durchschnitt(Notenpunkte): " + String.format("%.2f", summe_notenpunkte / noten.size()) );
            writer.newLine();
            writer.append("Durchschnitt(Noten): " + String.format("%.2f", summe_noten / noten.size()) );
            writer.newLine();
            writer.newLine();
            writer.append("Zuletzt " + now + " aktualisiert");

            writer.close();

            File logTxt = new File(dateiWeg + "/log.txt");
            FileWriter fileWriter = new FileWriter(logTxt, true);
            writer = new BufferedWriter(fileWriter);

            writer.newLine();
            writer.append(String.format("%.0f", summe_notenpunkte) + " Notenpunkte | " + now);

            writer.close();

            System.out.println("Berechnung ist zu Ende!");
            System.out.println("Siehe statistik.txt und noten.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}