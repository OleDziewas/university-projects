class LinkedListProperties {

    public static boolean belongsInCircularList(Element e) {

        Element count = e;
        while (count.nextElement!=e){
            if (count.nextElement == null){
                return false;
            }
            count = count.nextElement;
        }
        return true;

    }

    public static boolean belongsInPeriodicList(Element e) {
        //Länge der Liste ermitteln
        Element count = e;
        int len = 1;
        while (count.nextElement != e){
            if (count.nextElement == null){
                break;
            }
            count = count.nextElement;
            len++;
        }

        Element[] eList = new Element[len];
        Element start = e;
        for (int i = 0; i<len; i++){
            eList[i]= start;
            start= start.nextElement;
        }
        for (int i = 0; i<len; i++){
            System.out.println(eList[i].value);
        }

        return false;
    }

}
