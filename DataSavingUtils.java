import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Type;
import java.security.AlgorithmParameters;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class DataSavingUtils {
    private String filename;

    private static final String SECRET_KEY = "TheBestSecretKeyYouCanImagine";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_FACTORY = "PBKDF2WithHmacSHA256";
    private static final String SALT = "2947283829";

    public DataSavingUtils(String filename) {
        this.filename = filename;
    }

    public void saveToJsonFile(Object o) {
        try (Writer writer = new FileWriter(this.filename)) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();
            String jsonData = gson.toJson(o);
            String encryptedData = encrypt(jsonData);
            writer.write(encryptedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> readFromJsonFile() {
        File file = new File(this.filename);
        if (file.exists() && file.length() > 0) {
            ArrayList<Player> players = new ArrayList<>();
            try (Reader reader = new FileReader(this.filename)) {
                StringBuilder encryptedData = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    encryptedData.append((char) c);
                }

                String decryptedData = decrypt(encryptedData.toString());

                Type type = new TypeToken<ArrayList<Player>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                players = gson.fromJson(decryptedData, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return players;
        } else return new ArrayList<Player>();
    }

    public static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(DATE_FORMATTER.format(date));
        }

        @Override
        public LocalDate deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(element.getAsString(), DATE_FORMATTER);
        }
    }

    private String encrypt(String data) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_FACTORY);
            KeySpec spec = new javax.crypto.spec.PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] cipherText = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            System.out.println("Can't encrypt file");
        }
        return null;
    }

    private String decrypt(String encryptedData) {
        try {
            String[] parts = encryptedData.split(":");
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] cipherText = Base64.getDecoder().decode(parts[1]);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_FACTORY);
            KeySpec spec = new javax.crypto.spec.PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            System.out.println("Can't decrypt file");
        }
        return null;
    }
}
