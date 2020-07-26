package com.mortgagedemo.app.repository;

import org.springframework.stereotype.Repository;

import com.mortgagedemo.app.model.Mortgage;

//Java program deals with all operation of a dynamic array 
//add, remove, resize memory of array is the main feature 
@Repository
public class MortgageDynamicArray implements Cloneable {

	// create three variable array[] is a array,
	// count will deal with no of element add by you and
	// size will with size of array[]
	public Mortgage array[];
	private int count;
	public int size;
	// constructor initialize value to variable

	@Override
	public MortgageDynamicArray clone() throws CloneNotSupportedException {
		MortgageDynamicArray mortgageDynamicArray = (MortgageDynamicArray) super.clone();
		mortgageDynamicArray.array = this.array.clone();
		return mortgageDynamicArray;
	}

	public MortgageDynamicArray() {
		array = new Mortgage[1];
		count = 0;
		size = 1;
	}
	// function add an element at the end of array

	public Mortgage add(Mortgage mortgage) {

		// check no of element is equql to size of array
		if (count == size) {
			growSize(); // make array size double
		} // insert element at end of array
		array[count] = mortgage;
		count++;
		shrinkSize();
		return mortgage;
	}

	// function makes size double of array
	public void growSize() {

		Mortgage temp[] = null;
		if (count == size) {

			// temp is a double size array of array
			// and store array elements
			temp = new Mortgage[size * 2];
			{
				for (int i = 0; i < size; i++) {
					// copy all array value into temp
					temp[i] = array[i];
				}
			}
		}

		// double size array temp initialize
		// into variable array again
		array = temp;

		// and make size is double also of array
		size = size * 2;
	}

	// function shrink size of array
	// which block unnecessary remove them
	public void shrinkSize() {
		Mortgage temp[] = null;
		if (count > 0) {

			// temp is a count size array
			// and store array elements
			temp = new Mortgage[count];
			for (int i = 0; i < count; i++) {

				// copy all array value into temp
				temp[i] = array[i];
			}

			size = count;

			// count size array temp initialize
			// into variable array again
			array = temp;
		}
	}

	// function add an element at given index
	public Mortgage set(int index, Mortgage mortgage) { // if size is not enough make size double
		if (count == size) {
			growSize();
		}
		// update data at given index
		array[index] = mortgage;
		// removeAt(index+1);
		count++;
		shrinkSize();
		return mortgage;
	}

	public Mortgage addAt(int index, Mortgage mortgage) {
		// if size is not enough make size double
		if (count == size) {
			growSize();
		}

		for (int i = count - 1; i >= index; i--) {

			// shift all element right
			// from given index
			array[i + 1] = array[i];
		}

		// insert data at given index
		array[index] = mortgage;
		// removeAt(index+1);
		count++;
		shrinkSize();
		return mortgage;
	}

	// function remove last element or put
	// zero at last index
	public void remove() {
		if (count > 0) {
			array[count - 1] = null;
			count--;
		}
	}

	// function shift all element of right
	// side from given index in left
	public void removeAt(int index) {
		if (count > 0) {
			for (int i = index; i < count - 1; i++) {

				// shift all element of right
				// side from given index in left
				array[i] = array[i + 1];
			}
			array[count - 1] = null;
			count--;
		}
	}

	public void removeNull(Mortgage mortgage) {
		MortgageDynamicArray mortgageDynamicArray = new MortgageDynamicArray();
		if (count > 0) {
			for (int i = 0; i < count - 1; i++) {

				// shift all element of right
				// side from given index in left
				// array[i] = array[i + 1];
				if (array[i] != null) {
					mortgageDynamicArray.array[i] = array[i];
				} else {
					i++;
					count--;
				}
			}
			// array[count - 1] = null;
			// count--;
		}
	}

	public boolean isAlreadyPresentInMortgageDynamicArray(MortgageDynamicArray mortgageDynamicArray,
			Mortgage mortgage) {
		boolean flag = false;
		if (mortgageDynamicArray.size < 1) {
			System.out.println("List is empty");
			return flag;
		}
		for (int i = 0; i < mortgageDynamicArray.size; i++) {
			System.out.println(mortgageDynamicArray.array[i] + " ");
			if (mortgageDynamicArray.array[i] != null && mortgageDynamicArray.array[i].equals(mortgage)) {
				flag = true;
				break;
			}
		}
		if (flag)
			System.out.println("Entity Already Exists");
		else
			System.out.println("Entity Not Exists");
		return flag;
	}

	// public static void main(String[] args) {
	/*
	 * MortgageDynamicArray da = new MortgageDynamicArray(); Date todaysDate = new
	 * Date(); SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	 * String createdDate = formatter.format(todaysDate); // add 9 elements in array
	 * Mortgage mortgage1 = new Mortgage("M1", 1, "OI-1", "B1", "20/05/2020",
	 * createdDate, "N"); Mortgage mortgage2 = new Mortgage("M2", 2, "OI-2", "B1",
	 * "20/05/2021", createdDate, "N"); Mortgage mortgage3 = new Mortgage("M3", 1,
	 * "OI-1", "B1", "20/05/2021", createdDate, "N"); Mortgage mortgage4 = new
	 * Mortgage("M4", 3, "OI-3", "B2", "20/05/2014", createdDate, "Y");
	 * mortgageDynamicArray.add(mortgage1); mortgageDynamicArray.add(mortgage2);
	 * mortgageDynamicArray.add(mortgage3); mortgageDynamicArray.add(mortgage4);
	 * 
	 * 
	 * // print all array elements after add 9 elements
	 * System.out.println("Elements of array:"); for (int i = 0; i <
	 * mortgageDynamicArray.size; i++) {
	 * System.out.println(mortgageDynamicArray.array[i] + " "); }
	 */

	// System.out.println();
	/*
	 * // print size of array and no of element System.out.println("Size of array: "
	 * + mortgageDynamicArray.size); System.out.println("No of elements in array: "
	 * + mortgageDynamicArray.count);
	 * 
	 * // shrinkSize of array mortgageDynamicArray.shrinkSize();
	 * 
	 * // print all array elements System.out.println("Elements of array "+
	 * "after shrinkSize of array:"); for (int i = 0; i < mortgageDynamicArray.size;
	 * i++) { System.out.print(mortgageDynamicArray.array[i] + " "); }
	 * System.out.println();
	 * 
	 * // print size of array and no of element System.out.println("Size of array: "
	 * + mortgageDynamicArray.size); System.out.println("No of elements in array: "
	 * + mortgageDynamicArray.count);
	 * 
	 * // add an element at index 1 mortgageDynamicArray.addAt(1, 22);
	 * 
	 * // print Elements of array after adding an // element at index 1
	 * System.out.println("Elements of array after" +
	 * " add an element at index 1:"); for (int i = 0; i <
	 * mortgageDynamicArray.size; i++) {
	 * System.out.print(mortgageDynamicArray.array[i] + " "); }
	 * 
	 * System.out.println();
	 * 
	 * // print size of array and no of element System.out.println("Size of array: "
	 * + mortgageDynamicArray.size); System.out.println("No of elements in array: "
	 * + mortgageDynamicArray.count);
	 * 
	 * // delete last element mortgageDynamicArray.remove();
	 * 
	 * // print Elements of array after delete last // element
	 * System.out.println("Elements of array after" + " delete last element:"); for
	 * (int i = 0; i < mortgageDynamicArray.size; i++) {
	 * System.out.print(mortgageDynamicArray.array[i] + " "); }
	 * 
	 * System.out.println();
	 * 
	 * // print size of array and no of element System.out.println("Size of array: "
	 * + mortgageDynamicArray.size); System.out.println("No of elements in array: "
	 * + mortgageDynamicArray.count);
	 * 
	 * // delete element at index 1 mortgageDynamicArray.removeAt(1);
	 * 
	 * // print Elements of array after delete // an element index 1
	 * System.out.println("Elements of array after"+ " delete element at index 1:");
	 * for (int i = 0; i < mortgageDynamicArray.size; i++) {
	 * System.out.print(mortgageDynamicArray.array[i] + " "); }
	 * System.out.println();
	 * 
	 * // print size of array and no of element System.out.println("Size of array: "
	 * + mortgageDynamicArray.size); System.out.println("No of elements in array: "
	 * + mortgageDynamicArray.count);
	 */
	// }
}
