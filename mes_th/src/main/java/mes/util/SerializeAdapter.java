package mes.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public final class SerializeAdapter {
	public SerializeAdapter() {

	}

	public String toString(Serializable ser) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(ser);
		byte[] bs = baos.toByteArray();

		StringBuffer obj = new StringBuffer();
		for (byte b : bs) {
			obj.append(b);
			obj.append(',');
		}
		oos.close();
		baos.close();
		return obj.toString();
	}

	public Object toObject(String str) throws IOException,
			ClassNotFoundException {
		String[] strings = str.split(",");
		byte[] bs2 = new byte[strings.length];
		for (int i = 0; i < strings.length; i++) {
			bs2[i] = Byte.valueOf(strings[i]);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(bs2);
		ObjectInputStream ois = new ObjectInputStream(bais);

		return ois.readObject();
	}
	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		Vector<String> list = new Vector<String>();
		list.add("dd");
		list.add("ee");
		list.add("ff");
		SerializeAdapter sa = new SerializeAdapter();
		try {
			String str = sa.toString(list);
			Vector<String> list2 = (Vector<String>)sa.toObject(str);
			System.out.println(list+":"+list2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
