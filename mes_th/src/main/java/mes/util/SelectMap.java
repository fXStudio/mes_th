package mes.util;

/**
 *  select标签中的Map的排序
 * @author lida
 *
 */
public class SelectMap implements Comparable<SelectMap>{
 	
		int val = -1;
		String lab = "";
		
		public SelectMap(int val,String lab){
			this.val = val;
			this.lab = lab;
		}
		
		public int compareTo(SelectMap o){
			if(o==null)
				return 1;
			if(this.val>o.val)return 1;
			if(this.val<o.val)return -1;
			return 0;	
		}
		
		public String toString(){
			return lab;
		}
	}

/*
 * gather_update.jsp 页面上的例子
 * <%!
 	class Opt implements Comparable<Opt>{
 	
 		int val = -1;
 		String lab = "";
 		
 		Opt(int val,String lab){
 			this.val = val;
 			this.lab = lab;
 		}
 		
 		public int compareTo(Opt o){
 			if(o==null)
 				return 1;
 			if(this.val>o.val)return 1;
 			if(this.val<o.val)return -1;
 			return 0;	
 		}
 		
 		public String toString(){
 			return lab;
 		}
 	}
  %>
 */
