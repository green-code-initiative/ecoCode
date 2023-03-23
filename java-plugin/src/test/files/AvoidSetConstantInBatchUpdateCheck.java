package fr.greencodeinitiative.java.checks;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AvoidSetConstantInBatchUpdateCheck {

    void literalSQLrequest() { //dirty call

        int x = 0;
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
        PreparedStatement stmt = con.prepareStatement("insert into Emp values(?,?,?,?)");
        stmt.setInt(1, 101);
        stmt.setString(2, "Ratan");
        stmt.setBigDecimal(3, Bigdecimal.ONE);
        stmt.setBigDecimal(4, BigDecimal.valueOf(x));
        stmt.setBoolean(5, Boolean.valueOf("true"));
        int i = stmt.executeUpdate();
        System.out.println(i + " records inserted");
        con.close();
    }

    void batchInsertInForLoop(int[] data) {

        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
        PreparedStatement stmt = con.prepareStatement("insert into Emp values(?,?,?,?,?,?,?,?,?,?,?)");
        for (int i = 0; i < data.length; i++) {
            stmt.setInt(1, data[i]);

            stmt.setBoolean(2, true); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setByte(3, (byte) 3); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setByte(4, 'v'); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setShort(5, (short) 5); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setInt(6, 6); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setLong(7, (long) 7); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setLong(7, 7l); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setFloat(8, (float) 8.); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setFloat(8, 8.f); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setDouble(9, 9.); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setDouble(9, 9.); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setString(10, "10"); // Noncompliant {{Avoid setting constants in batch update}}
            stmt.setBigDecimal(11, BigDecimal.valueOf(.77));  // Noncompliant {{Avoid setting constants in batch update}}
            stmt.addBatch();
        }
        int[] nr = stmt.executeBatch();
        logger.log("{} rows updated", IntStream.of(nr).sum());
        con.close();
    }


    int[] batchInsertInForeachLoop(DummyClass[] data) {

        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle")) {
            PreparedStatement stmt = con.prepareStatement("insert into Emp values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            for (DummyClass o : data) {
                stmt.setInt(1, o.getField1());
                stmt.setBoolean(2, Boolean.valueOf("false")); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setByte(3, o.getField3());
                stmt.setByte(4, 'v'); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setShort(5, (short) 5); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setInt(6, 6); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setLong(7, 7); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setFloat(8, (float) 8.); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setDouble(9, o.getField4());
                stmt.setString(10, o.getField2());
                stmt.setBigDecimal(11, BigDecimal.valueOf(11)); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.addBatch();
            }
            return stmt.executeBatch();
        }
    }


    int[] batchInsertInWhileLoop(DummyClass[] data) {

        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle")) {
            PreparedStatement stmt = con.prepareStatement("insert into Emp values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            int i = 0;
            while (i < data.length) {
                DummyClass o = data[i];
                stmt.setInt(1, o.getField1());
                stmt.setBoolean(2, Boolean.TRUE); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setByte(3, o.getField3());
                stmt.setByte(4, Byte.MAX_VALUE); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setByte(4, Character.MAX_VALUE); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setShort(5, Short.MIN_VALUE); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setInt(6, Integer.MAX_VALUE); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setLong(7, Long.MIN_VALUE); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setFloat(8, Float.MAX_VALUE); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setDouble(9, Double.MIN_VALUE);  // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setString(10, o.getField2());
                stmt.setBigDecimal(11, BigDecimal.TEN); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.addBatch();
                i++;
            }
            return stmt.executeBatch();
        }
    }

    int[] batchInsertInWhileLoop(DummyClass[] data) {
        if (data.length == 0) {
            return new int[]{};
        }
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle")) {
            PreparedStatement stmt = con.prepareStatement("insert into Emp values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            int i = 0;
            do {
                DummyClass o = data[i];
                stmt.setInt(1, o.getField1());
                stmt.setBoolean(2, Boolean.valueOf(true)); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setByte(3, o.getField3());
                stmt.setByte(4, Byte.valueOf((byte) 3)); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setByte(4, Character.valueOf('1')); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setShort(5, Short.valueOf((short) 55)); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setInt(6, Integer.valueOf("222")); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setLong(7, Long.valueOf(0)); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setFloat(8, Float.valueOf(.33)); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setDouble(9, Double.valueOf(22));  // Noncompliant {{Avoid setting constants in batch update}}
                stmt.setString(10, o.getField2());
                stmt.setBigDecimal(11, BigDecimal.valueOf(11)); // Noncompliant {{Avoid setting constants in batch update}}
                stmt.addBatch();
                i++;
            } while (i < data.length);
            return stmt.executeBatch();
        }
    }

    class DummyClass {

        public int getField1() {
            return 0;
        }

        public String getField2() {
            return "";
        }

        public byte getField3() {
            return 'A';
        }

        public double getField4() {
            return .1; }
	}
	

}