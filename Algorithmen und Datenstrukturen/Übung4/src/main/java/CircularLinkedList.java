class CircularLinkedList {
    public int len = 0;
    public Element head;

    public void addElement(long value) {
        if (len == 0){
            head = new Element(value);
            head.nextElement = head;
            len++;
        }else {
            Element count = head;
            while(count.nextElement!=head){
                count = count.nextElement;
            }
            count.nextElement = new Element(value);
            count.nextElement.nextElement = head;
            len++;
        }
    }

    public boolean containsElement(long value) {
        if (len == 0){
            return false;
        }
        Element count = head;
        if (count.value==value){
            return true;
        }
        count = count.nextElement;
        while(count != head){
            if (count.value == value){
                return true;
            }
            count = count.nextElement;
        }
        return false;

    }

    public void deleteElement(long value) {
        boolean emptyEl = false;
        while(!emptyEl) {
            if (len == 0) {
                System.out.println("Leere Liste");
                emptyEl = true;
            } else {
                //Head Element wird gelöscht
                if (head.value == value) {
                    if (len == 1) {
                        head.nextElement = null;
                        head = null;
                        len--;
                    } else {
                        head = head.nextElement;
                        Element count = head;
                        while (count.nextElement.nextElement != head) {
                            count = count.nextElement;
                        }
                        count.nextElement = head;
                        len--;
                    }
                    //Element ungleich head wird gelöscht
                } else {
                    Element count = head;
                    while (count.nextElement.value != value && count.nextElement != head) {
                        count = count.nextElement;
                    }
                    if (count.nextElement == head) {
                        System.out.println("Element nicht vorhanden");
                        emptyEl = true;
                    } else {
                        count.nextElement = count.nextElement.nextElement;
                        len--;
                    }
                }
            }
        }
    }

    public String traverseList() {
        if (len == 0){
            return "";
        }
        Element count = head;
        String trav = "";
        trav += count.value;
        count = count.nextElement;
        while (count != head) {
            trav += " " + count.value;
            count = count.nextElement;
        }

        return trav;

    }

    public int lengthOfList() {

        return len;

    }

}
class Element {

    long value;
    Element nextElement;

    public Element ( long value ) {
        this.value = value;
    }


}


