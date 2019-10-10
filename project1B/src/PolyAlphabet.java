public class PolyAlphabet {
    public String cypherText;
    public int C1 = 5; //change depending on what teacher says
    public int C2 = 19;

    public void setCypherText(String cypherText) {
        this.cypherText = cypherText;
    }

    public String encrypt(){
        StringBuffer result = new StringBuffer();
        int mod2 = 0;
        for (int length = 0; length < cypherText.length(); length++){
            if(Character.isUpperCase(cypherText.charAt(length))){
                if(mod2 == 0 || mod2 == 3){
                    char ch = (char)((((int)cypherText.charAt(length) + C1 - 65)%26)+65);
                    result.append(ch);
                    mod2++;
                }
                else {
                    char ch = (char) ((((int) cypherText.charAt(length) + C2 - 65) % 26) + 65);
                    result.append(ch);
                    mod2++;
                    if(mod2 == 5)
                        mod2 = 0;
                }
            }
            else if(Character.isLowerCase(cypherText.charAt(length))){
                if(mod2 == 0 || mod2 == 3){
                    char ch = (char)((((int)cypherText.charAt(length) + C1 - 97)%26)+97);
                    result.append(ch);
                    mod2++;
                }
                else {
                    char ch = (char) ((((int) cypherText.charAt(length) + C2 - 97) % 26) + 97);
                    result.append(ch);
                    mod2++;
                    if(mod2 == 5)
                            mod2 = 0;
                }
            }
            else{
                result.append(cypherText.charAt(length));
            }
        }
        return result.toString();
    }
}
