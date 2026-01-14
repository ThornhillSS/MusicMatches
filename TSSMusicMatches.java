

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class MusicProject2 {
    interface Question {
        double getDistance(Question q);
        void parse(String value);
    }
    static class ScaleQuestion implements Question{
        public int curScale;
        private List<String> orderedChoices;
        ScaleQuestion(List<String> orderedChoices) {
            this.orderedChoices = orderedChoices;
        }
        @Override
        public double getDistance(Question q) {
            return Math.pow(curScale - ((ScaleQuestion)q).curScale, 2);
        }

        @Override
        public void parse(String value) {
            curScale = orderedChoices.indexOf(value);
        }
    }
    static class MatchQuestion implements Question{
        public String choice;
        @Override
        public double getDistance(Question q) {
            return ((MatchQuestion)q).choice.equals(choice) ? 0 : 3;
        }

        @Override
        public void parse(String value) {
            choice = value;
        }
    }
    static class HueQuestion implements Question{
        public float hue;
        @Override
        public double getDistance(Question q) {
            HueQuestion hq = (HueQuestion) q;
            float d1 = Math.abs(hue - hq.hue);
            float d2 = Math.abs(hue - (1+hq.hue));
            return Math.min(d1, d2);
        }

        @Override
        public void parse(String str) {
            String colorStr = switch (str){
                case "Red" -> "#FF0000";
                case "Green" -> "#00FF00";
                case "Purple" -> "#A020F0";
                case "Blue" -> "#0000FF";
                case "Orange" -> "#FFA500";
                case "Pink" -> "#FFC0CB";
                case "White" -> "#FFFFFF";
                case "Yellow" -> "#FFFF00";
                default -> throw new IllegalStateException("Unexpected value: " + str);
            };
            Color col = new Color(
                    Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                    Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                    Integer.valueOf( colorStr.substring( 5, 7 ), 16 ));
            float[] hsv = new float[3];
            Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), hsv);
            hue = hsv[0];
        }
    }
    static class Entry {
        // col 3-4
        public String name;
        // col 5 [9-12]
        public int grade;
        public ArrayList<Question> questions = new ArrayList<>();
    }

    public static void addQuestion(Entry user, Question q, String[] input, int idx){
        q.parse(input[idx]);
        user.questions.add(q);
    }



    public static void buildQuestions(Entry user, Scanner input){
        String ln = input.nextLine();
        String[] spl = ln.split("\t");
        int curIndex = 0;
        // skip timestamp & email
        curIndex += 2;
        user.name = spl[curIndex++] + " " + spl[curIndex++];
        String grade = spl[curIndex++];
        user.grade = Integer.parseInt(grade.split(" ")[1]
                .replace("+", ""));
        // skip paper copy
        curIndex++;

        // I listen to music...
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "Never",
                "Rarely",
                "Sometimes",
                "Often",
                "All the time"
        )), spl, curIndex++);

        // I listen to music...
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "Alone",
                "With a small group of friends",
                "With a large group of friends",
                "With everyone and anyone"
        )), spl, curIndex++);

        // I usually listen to music at…
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "Home",
                "School",
                "Work",
                "Park or outside"
        )), spl, curIndex++);

        // I like listening to music through...
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "Earbuds softly",
                "Headphones softly",
                "Speakers softly",
                "Earbuds loudly",
                "Headphones loudly",
                "Speakers loudly"
        )), spl, curIndex++);

        // I like listening to music the most to…
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "Procrastinate",
                "Chill",
                "Calm down",
                "Focus",
                "Get inspiration",
                "Exercise/dance"
        )), spl, curIndex++);

        // Do you choose your music based on your mood? (ex. happy music when you're happy, sad music when you're sad)
        addQuestion(user, new MatchQuestion(), spl, curIndex++);

        // How much do you like Rap? ( (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        // How much do you like Pop? (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        // How much do you like Jazz? (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        // How much do you like Country? (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        // How much do you like EDM (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);


        // How much do you like Hip Hop (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        // How important is music in your life? (0 - not important // 5 - super important)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        // What colour is the note G?
        addQuestion(user, new HueQuestion(), spl, curIndex++);

        // Who writes the best love songs?
        addQuestion(user, new MatchQuestion(), spl, curIndex++);

        // Does listening to music affect your ability to read?
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        //  Saddest Breakup Song:
        addQuestion(user, new MatchQuestion(), spl, curIndex++);

        // How well can you sightread music?
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        // What platform do you use the most to listen to music?
        addQuestion(user, new MatchQuestion(), spl, curIndex++);

        // Good music tastes like…
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "Instant ramen noodles",
                "A juicy steak with mashed potatoes and roasted vegetables",
                "All you can eat sushi",
                "Thick chocolate chip cookies with milk",
                "Creamy hot chocolate",
                "A box of doughnuts",
                "Other"
        )), spl, curIndex++);

        // Favourite love song lyric from here:
        addQuestion(user, new MatchQuestion(), spl, curIndex++);

        // How much do you like Classical Music? (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);

        //  How much do you like Asian Pop (K-Pop, J-Pop, C-Pop, etc) (0 - strong dislike // 5 - my absolute favourite)
        addQuestion(user, new ScaleQuestion(Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
        )), spl, curIndex++);
    }

    private static ArrayList<Entry> allEntries = new ArrayList<>();

    public static float calculateDistance(Entry p1, Entry p2){
        float cumSum = 0;
        for(int i = 0; i < p1.questions.size(); i++){
            cumSum += p1.questions.get(i).getDistance(p2.questions.get(i));
        }
        return (float) Math.sqrt(cumSum);
    }

    static class Pair{
        public Entry entry;
        public float score;

        public Pair(Entry entry, float score) {
            this.entry = entry;
            this.score = score;
        }
    }

    public static List<Pair> getSortedList(Entry person){
        ArrayList<Pair> list = new ArrayList<>();
        for(Entry e : allEntries){
            if(e != person){
                list.add(new Pair(e, calculateDistance(e, person)));
            }
        }
        list.sort(((o1, o2) -> Float.compare(o1.score, o2.score)));
        return list.stream().toList();
    }

    public static List<Pair> getTop10(Entry person){
//        return getSortedList(person).subList(0, 10);
        List<Pair> e = getSortedList(person).stream()
                .filter(x->x.entry.grade != person.grade).toList();
        return e.subList(0, Math.min(10, e.size()));
    }

    public static List<Pair> getTop10Grade(Entry person){
        List<Pair> e = getSortedList(person).stream()
                .filter(x->x.entry.grade == person.grade).toList();
        return e.subList(0, Math.min(10, e.size()));
    }

    public static List<Pair> getBottom5(Entry person){
        List<Pair> entries = new ArrayList<>(getSortedList(person));
        Collections.reverse(entries);
        return entries.subList(0, 5);
    }

    public static void printEntries(List<Pair> entry){
        for(Pair e : entry){
            System.out.println(e.entry.name + " " + Math.round(e.score));
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File textFile = new File("SoulmatesData2.tsv");
        Scanner input = new Scanner(textFile);
        int NUM_PEOPLE = 123;
        input.nextLine(); // skip header
        for(int i = 0; i < NUM_PEOPLE; i++){
            Entry user = new Entry();
            buildQuestions(user, input);
            allEntries.add(user);
        }

        for(int i = 0; i < NUM_PEOPLE; i++){
            Entry user = allEntries.get(i);
            System.out.println(" ---------------------------------- DATA FOR USER " + user.name);
            System.out.println("TOP 10 IN ALL OTHER GRADES");
            printEntries(getTop10(user));
            System.out.println();
            System.out.println("TOP 10 IN GRADE");
            printEntries(getTop10Grade(user));
            System.out.println();
            System.out.println("BOTTOM 5 IN ALL GRADES");
            printEntries(getBottom5(user));
            System.out.println();
        }
    }
}
