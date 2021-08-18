import javax.xml.xpath.XPath;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class main {

    public static int X = 0;
    public static int Y = 0;
    public static int n = 0;
    public static int m = 0;
    public static int COLOR = 0;
    public static Integer[] arrMatr;

    public static void main(String[] args) {
        if (readFile("src/main/inputF.txt") == 0)
        {
            AtomicInteger row = new AtomicInteger();
            IntStream.range(0, arrMatr.length)
                    .forEach(
                            index ->
                            {
                                if (row.get() == X - 1) {
                                    row.set(enterRez(index, row.get(), arrMatr[index], Y + 1, 0));
                                } else if (row.get() == X) {
                                    row.set(enterRez(index, row.get(), arrMatr[index], Y + 3, 1));
                                } else if (row.get() == X + 1) {
                                    row.set(enterRez(index, row.get(), arrMatr[index], Y + 1, 0));
                                } else {
                                    row.set(enterRez(index, row.get(), arrMatr[index], 0, 0));
                                }
                            }
                    );
        }
    }

    //метод записи строки в файл
    public static Object writeFile(String str)
    {
        String filePath = "inputF.out";
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(str); //записываем строку
            bufferWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }


    //метод записи результата
    //trend - направление относительно X ( 0 - для index = Х+1 или Х-1, 1 для index = Х
    public static void printRez(int index, int arrZn, int offset, int trend, int endEl) {
        if ( offset == 0) // для ячеек матрицы, вне рисунка
        {
            //если элемент последний, то добавть в конце \n
            String data = endEl == 0 ? String.valueOf(arrZn)+" \n" : String.valueOf(arrZn)+" ";
            writeFile(data);
        }
        else // для ячеек матрицы с рисунком
        {
            if (endEl == 0) { //проверка на последний элемент
                if (trend == 0 ) // строка равна X-1 или X+1
                {
                    // провермяем индекс ячейки == offset == Y( + 1) - возможная ячейка для рисунка,
                    //если совпадает со значением для закрашивания рисунка (одна клетка),
                    //то заполняем цветом, если нет, то оставляем без имзенений
                    String data = index % m == offset ? String.valueOf(COLOR)+" \n" : String.valueOf(arrZn)+" \n";
                    writeFile(data); //запись в файл
                }
                else if (trend == 1) //строка равна X
                {
                    // провермяем индекс ячейки в строке, если совпадает со значениями для закрашивания рисунка (три клетки),
                    // то заполняем цветом, если нет, то оставляем без имзенений
                    String data = index % m >= offset - 3 && index % m < offset
                            ? String.valueOf(COLOR)+" \n" : String.valueOf(arrZn)+" \n";
                    writeFile(data);
                }
            }
            else if (endEl == 1) // не последний элемент
            {
                if (trend == 0) // строка равна X-1 или X+1
                {
                    // провермяем индекс ячейки == offset == Y( + 1) - возможная ячейка для рисунка,
                    //если совпадает со значением для закрашивания рисунка (одна клетка),
                    //то заполняем цветом, если нет, то оставляем без имзенений
                    String data = index % m == offset ? String.valueOf(COLOR)+" " : String.valueOf(arrZn)+" ";
                    writeFile(data);
                }
                else if (trend == 1) //строка равна X
                {
                    // провермяем индекс ячейки в строке, если совпадает со значениями для закрашивания рисунка (три клетки),
                    // то заполняем цветом, если нет, то оставляем без имзенений
                    String data = index % m >= offset - 3 && index % m < offset
                            ? String.valueOf(COLOR)+" " : String.valueOf(arrZn)+" ";
                    writeFile(data);
                }
            }
        }
    }


    //метод вывода результата
    //offset - граница для рисунка по Y
    //trend - направление относительно X ( 0 - для index = Х+1 или Х-1, 1 для index = Х
    public static int enterRez(int index, int row, int arrZn, int offset, int trend)
    {
        if (index % 5 == 4) { // проверяем, является ли элемент послденим в строке
            row++; //перенос строки
            printRez(index, arrZn, offset, trend, 0);
        }
        else { // не последний в строке
            printRez(index, arrZn, offset, trend, 1);
        }
        return row;
    }

    public static int readFile(String path)
    {
        List<String> allLines = new ArrayList<>();
        Path file = Paths.get(path);
        //считываем содержимое файла в массив байт
        try {
            byte[] bytes = Files.readAllBytes(file);
            //считываем содержимое файла в список строк
            allLines = Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }

        //считываем размерность матрицы
        String[] arrayStrings = allLines.get(0).split(" ");
        n = Integer.parseInt (arrayStrings[0]);
        m = Integer.parseInt (arrayStrings[1]);
        //проверяем значения размерности матрицы
        if( n > 255 || n < 0 || m > 255 || m < 0) return 1;

        //считываем координаты рисунка
        arrayStrings = allLines.get(1).split(" ");
        X = Integer.parseInt (arrayStrings[0]);
        Y = Integer.parseInt (arrayStrings[1]);
        //проверяем значения координат
        if ( X < 0 || X > n || Y < 0 || Y > m) return 1;

        //считываем код цвета заливки
        arrayStrings = allLines.get(2).split(" ");
        COLOR = Integer.parseInt (arrayStrings[0]);
        if (COLOR < 0 || COLOR > 255 ); //проверяем значение цвета

        List<Integer> matrix = new LinkedList<>();
        for(int i = 0; i < n; i++)
        {
            arrayStrings = allLines.get(i+3).split(" ");
            for(String a: arrayStrings)
            {
                if (Integer.parseInt(a) < 0 || Integer.parseInt(a) > 255) return 1;
                matrix.add(Integer.parseInt(a)); //считываем матрицу
            }
        }

        arrMatr = matrix.toArray(new Integer[matrix.size()]);
        return 0;
    }
}
