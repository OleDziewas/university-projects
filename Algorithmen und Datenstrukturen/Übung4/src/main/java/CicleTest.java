public class CicleTest {
    public static void main(String[] args) {
//        CircularLinkedList cic = new CircularLinkedList();
//        cic.addElement(7);
//        cic.addElement(9);
//        cic.addElement(11);
//        System.out.println(cic.traverseList());
//        System.out.println(cic.containsElement(11));
//        System.out.println(cic.containsElement(7));
//        cic.deleteElement(7);
//        System.out.println(cic.containsElement(7));
//        System.out.println(cic.lengthOfList());
//        cic.deleteElement(11);
//        System.out.println(cic.traverseList());
//        cic.deleteElement(9);
//        cic.deleteElement(5);
//        System.out.println(cic.containsElement(11));
//        System.out.println(cic.lengthOfList());
//        System.out.println(cic.traverseList());
        CircularLinkedList cic = new CircularLinkedList();

        cic.addElement(9);
        cic.addElement(8);
        cic.addElement(9);
        cic.addElement(8);
        cic.addElement(5);
        cic.deleteElement(9);
        cic.deleteElement(8);
        System.out.println(cic.lengthOfList());
        System.out.println(cic.head.value);



    }
}
