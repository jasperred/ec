import java.util.ArrayList;
import java.util.List;

public class test2 {
	public static void main(String[] args) {
		List l = new ArrayList();
		for(int i=0;i<10;i++)
			l.add(i);
		List l1 = l.subList(0, 6);
		List l2 = l.subList(6, 10);
		System.out.println("");
	}
}
