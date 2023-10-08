import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger for3 = new AtomicInteger();
    public static AtomicInteger for4 = new AtomicInteger();
    public static AtomicInteger for5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPolindrome(text) && isSameChar(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        palindrome.start();

        Thread sameChar = new Thread(() -> {
            for (String text : texts) {
                if (isSameChar(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        sameChar.start();

        Thread ascending = new Thread(() -> {
            for (String text : texts) {
                if (!isSameChar(text) && isAscendingOrder(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        ascending.start();

        palindrome.join();
        sameChar.join();
        ascending.join();

        System.out.println("Красивых слов с длинной 3: " + for3);
        System.out.println("Красивых слов с длинной 4: " + for4);
        System.out.println("Красивых слов с длинной 5: " + for5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPolindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSameChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscendingOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            for3.getAndIncrement();
        } else if (textLength == 4) {
            for4.getAndIncrement();
        } else {
            for5.getAndIncrement();
        }
    }
}
