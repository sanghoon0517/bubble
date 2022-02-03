package bubble.test;

import lombok.Data;

@Data
class Dog{
	private String name;
}
public class LombokTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dog d = new Dog();
		d.setName("santa");
		System.out.println(d.getName());
	}

}
