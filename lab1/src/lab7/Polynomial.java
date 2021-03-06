package lab7;

public class Polynomial{
	Monomial[] exps = new Monomial[100];
	int tot; 
	public Polynomial() {
	} 
	public Polynomial(Polynomial o) {
		for(int i=0;i<o.tot;i++)
		if(o.exps[i] != null)
		{
			exps[i] = new Monomial(o.exps[i]);
		}
		tot = o.tot;
	}
	public void clear() {
		tot = 0;
		exps = new Monomial[100];
	} 
	public Boolean empty() {
		return tot == 0;
	} 
	public void push(Monomial Monomial) {
		exps[++tot] = Monomial;
	} 
	public Boolean addMonomial(String str) { 
		char operator = str.charAt(0);
		str = str.trim();
		if (isindexs(str)) {
			push(new Monomial("", toindexs(str)));
			return true;
		}
		if (isVar(str)) {
			if (operator == '+') {
				push(new Monomial(str.substring(1, str.length()), 1));
			} else if (operator == '-') {
				push(new Monomial(str.substring(1, str.length()), -1));
			} else {
				push(new Monomial(str, 1));
			}
			return true;
		}
		int current = 0;
		int tmp = 1;
		int flag = 1;
		String nam = "";
		str += '*';
		if (operator == '+') {
			current = 1;
		} else if (operator == '-') {
			current = 1;
			flag = -1;
		}
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) == '*') {
				String temp = str.substring(current, i);
				// System.out.println(temp+' '+i);
				if (isVar(temp)) {
					nam += temp + '*';
				} else if (isindexs(temp)) {
					int toN = toindexs(temp);
					tmp *= toN;
				} else {
					return false;
				}
				current = i + 1;
			}
		}
		push(new Monomial(nam, tmp * flag));
		return true;
	} 
	private int toindexs(String str) { 
		int tmp = 0;
		int flag = 1;
		char operator = str.charAt(0);
		if (operator == '-') {
			flag = -1;
		} else if (operator >= '0' && operator <= '9') {
			tmp = operator - '0';
		}
		for (int i = 1; i < str.length(); i++) {
			operator = str.charAt(i);
			tmp = tmp * 10 + operator - '0';
		}
		return tmp * flag;
	} 
	private Boolean isindexs(String str) { 
		char operator = str.charAt(0);
		if (operator != '+' && operator != '-'
				&& (operator < '0' || operator > '9')) {
			return false;
		}
		for (int i = 1; i < str.length(); i++) {
			if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
				return false;
			}
		}
		return true;
	} 
	private Boolean isVar(String str) {  
		for (int i = 0; i < str.length(); i++) {
			char operator = str.charAt(i);
			if ((operator >= '0' && operator <= '9')
					|| (operator == '*' || operator == '^' || operator == ' ')) {
				return false;
			}
		}
		return true;
	} 
	public String simplify(String str) {  
		String vars[] = new String[100];
		int indexs[] = new int[100];
		int top = 0;
		int current = 0;
		str += ' ';
		for (int i = 0; i < str.length(); i++) {
			char operator = str.charAt(i);
			if (operator == ' ') {
				for (int j = current; j < i; j++) {
					if (str.charAt(j) == '=') {
						vars[top] = str.substring(current, j);
						indexs[top++] = toindexs(str.substring(j + 1, i));
						// System.out.println(Vars[top-1] + '=' + indexs[top-1]);
						break;
					}
				}
				current = i + 1;
			}
		}
		String ans = "";
		for (int i = 1; i <= tot; i++) {
			Monomial tmp = new Monomial(exps[i]);
			// System.out.println(tmp.coe);
			for (int j = 0; j < tmp.top; j++) {
				for (int k = 0; k < top; k++) {
					if (vars[k].equals(tmp.vars[j])) {
						int abc = indexs[k];
						for (int r = 0; r < tmp.indexs[j]; r++) {
							tmp.coe *= abc;
						}
						tmp.indexs[j] = 0;
					}
				}
			}
			if (tmp.coe < 0) {
				ans += tmp.coe;
			} else {
				ans += "+" + tmp.coe;
			}
			for (int j = 0; j < tmp.top; j++) {
				for (int k = 0; k < tmp.indexs[j]; k++) {
					ans += '*' + tmp.vars[j];
				}
			}
		}
		//System.out.println(ans.substring(1));
		return ans.substring(1);
	} 
	public String derivative(String str) {  
		String vars[] = new String[100];
		int top = 0;
		vars[top++] = str;
		String ans = "";
		for (int i = 1; i <= tot; i++) {
			Monomial tmp = new Monomial(exps[i]);
			// System.out.println(tmp.coe);
			for (int j = 0; j < tmp.top; j++) {
				for (int k = 0; k < top; k++) {
					if (vars[k].equals(tmp.vars[j])) {
						if (tmp.indexs[j] > 0) {
							tmp.coe *= tmp.indexs[j];
							tmp.indexs[j]--;
							// System.out.println(Vars[k]);
						}
					}
				}
			}
			if (tmp.coe < 0) {
				ans += tmp.coe;
			} else {
				ans += "+" + tmp.coe;
			}
			for (int j = 0; j < tmp.top; j++) {
				for (int k = 0; k < tmp.indexs[j]; k++) {
					ans += '*' + tmp.vars[j];
				}
			}
		}
		return ans.substring(1);
		//System.out.println(ans.substring(1));
	} 
}
