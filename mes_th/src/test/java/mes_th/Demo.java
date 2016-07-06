package mes_th;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Demo {
	@Test
	public void print() {
		List<Integer> list = Arrays.asList(new Integer[] { 2, 3, 4, 5, 6, 7, 8, 9 });
		List<Integer> temp = new ArrayList<Integer>(list.size());

		for (int i = 0; temp.size() < list.size();) {
			System.out.println(list.get(i++));
			temp.add(0, 1);
		}
	}
}
