package client.whitedev.mods.botter.repository;

public class MethodRep {
    private String[] methods = new String[100];
    private static MethodRep methodRep;
    private String selectedMethod;
    private int currentIndex = 0;

    public static MethodRep getInstance(){
        if(methodRep == null){
            methodRep = new MethodRep();
        }
        return methodRep;
    }

    public String[] getMethods() {
        return methods;
    }

    public String getSelectedMethod() {
        return selectedMethod;
    }

    public void addToMethod(String name){
        for (int i = 0; i < methods.length; i++) {
            if(methods[i] == null)  {
                methods[i] = name;
                break;
            }
        }
    }

    private boolean isEmpty(){
        boolean isEmpty = true;
        for (String method : methods) {
            if (method != null) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    public String changeSelectedMethod() {
        if (isEmpty()) { return ""; }
        String selectedMethod = methods[currentIndex];
        if(selectedMethod == null){
            currentIndex = 0;
            selectedMethod = methods[currentIndex];
            return selectedMethod;
        }
        currentIndex = (currentIndex + 1) % methods.length;
        this.selectedMethod = selectedMethod;
        return selectedMethod;
    }


}
