var mes;if(mes==undefined){mes = new Object();}if(mes.taglib==undefined){mes.taglib=new Object();}
if(mes.taglib.tree==undefined){
	mes.taglib.tree = function(treeid){
		this.iml = new mes.taglib.imagelist();
		this.iml.path = "images/";
		this.iml.add("plus_top","collapse_top");
		this.iml.add("plus_end","collapse_end");
		this.iml.add("plus_m","collapse");

		this.iml.add("minus_top","expand_top");
		this.iml.add("minus_m","expand");
		this.iml.add("minus_end","expand_end");
		
		this.iml.add("branch_end","branch_end");
		this.iml.add("branch","branch");
		
		
		this.iml.add("fold2k_open","open");
		this.iml.add("fold2k_close","close");

		//this.iml.add();
		
		
		this.root = document.getElementById(treeid);
		this.target = '_self';
		this.subNum = 0;
		this.count=0;
		this.addNode = function(info,url,openIcon,closeIcon,parentnode){
			var node = this.createNode(info,url,parentnode);
			node.addNode = function(info,url,openIcon,closeIcon){
				return this.root.addNode(info,url,openIcon,closeIcon,this);
			};
			closeIcon = (closeIcon==undefined&&openIcon!=undefined)?openIcon:closeIcon
			
			node.openIcon = (openIcon!=undefined)?openIcon:node.openIcon;
			node.closeIcon = (closeIcon!=undefined)?closeIcon:node.closeIcon;
			//添加节点到树中，并修正其他节点的图标。
			var preNode = null;
			if(parentnode==undefined){
				this.root.appendChild(node);
				preNode = (this.root.count>=2)?this.root.childNodes[this.root.count-2]:null;
			}else{
				parentnode.menu.appendChild(node);
				preNode = (parentnode.count>=2)?parentnode.menu.childNodes[parentnode.count-2]:null;
			}
			if(preNode!=null)
				preNode.modifyIcon();
			node.modifyIcon();
			return node;
		};
		
		this.createNode = function(info,url,parentnode){
			var node = document.createElement("div");
			node.id = 'mes_taglib_tree_node'+this.subNum++;//唯一的id
			node.root = this;//root的关联
			node.parent = (parentnode==undefined)?node.root:parentnode;//父节点的关联
			node.tier = (parentnode==undefined)?1:parentnode.tier+1;//节点层次
			node.index = (parentnode==undefined)?this.count++:parentnode.count++;
			node.isExpand = true;
			node.openIcon = "open";
			node.closeIcon = "close";
			
				//在兄弟节点中的索引值
			if(parentnode!=undefined){parentnode.modifyIcon();}
				//在添加子节点的时候修正父节点所使用的图标信息
			node.count = 0;
			node.exIcon = new Image();
			node.exIcon.className = "treeicon";
			node.exIcon.src = this.iml.item["expand_top"].src;
			node.exIcon.parent = node;
			node.exIcon.align="absbottom";
			node.exIcon.onclick = function(){
			//	要点击图标部分的时候要做展开/折叠子节点动作
				var node = this.parent;
				node.isExpand = !node.isExpand;
				if(node.count>0){
					node.menu.style.display = node.isExpand?'block':'none';
				}
				node.modifyIcon();
			}  
			
			node.appendChild(node.exIcon);
			
			node.icon = new Image();
			node.icon.className = "treeicon";
			node.icon.src = this.iml.item["open"].src;
			node.icon.align="absbottom";
			node.appendChild(node.icon);
			
			node.info = document.createElement("span");//显示节点的文字信息
			node.info.innerHTML=info;
			//node.info.innerHTML+="(index:"+node.index+",parent:"+node.parent+",tier:"+node.tier+")";
			node.info.parent = node;
			node.info.onclick = function(){
				//当点击节点文字部分的时候要激活连接
				if(url!=undefined){	window.open(url,this.parent.root.target);}
				else{this.parent.exIcon.onclick();}
			}
			
			
			node.info.onmouseover = function(){
				node.info.style.color="#993300";
				
			} 
			node.info.onmouseout = function(){
				node.info.style.color="black";
			} 
			
			
			node.info.className = "treeNode";
			node.modifyIcon = function(){
				//在节点结构发生变化后修正节点所使用的图标方法。
				//打开和关闭的时候的图标有可能会被用户重新指定，所以这里要做有效性判断。
				var item = this.root.iml.item[this.isExpand?this.openIcon:this.closeIcon];
				this.icon.src = (item!=null)?item.src:this.icon.src;
				/*zzz由于图片不好，这里暂时不做了
				if(this.index==0){
					this.exIcon.src = this.root.iml.item[this.isExpand?"expand_top":"collapse_top"].src;				
					return;
				} else */
				if(this.index==this.parent.count-1){
					
						this.exIcon.src = this.root.iml.item[
							this.count==0?"branch_end":
							this.isExpand?"expand_end":"collapse_end"
							].src;				
					return;
				}
				else{
					this.exIcon.src = this.root.iml.item[
						this.count==0?"branch":
						this.isExpand?"expand":"collapse"
						].src;
				}
			}
			node.appendChild(node.info);
			
			node.menu = document.createElement("div");
			node.menu.style.paddingLeft="20px";
			node.menu.className = "treeNodeMenu";
			node.menu.style.paddingTop="1px";
			
			
	
		
			
			node.appendChild(node.menu);
			return node;
		};
		
		return this;
	};
	
	
};
if(mes.taglib.imagelist==undefined){
	mes.taglib.imagelist = function(){
		var item=[],count=0;
		this.add=function(src,key){
			if(src==null || src=="")return;
			if(src.indexOf("/")==-1)src=this.path+src;
			if(!(/\.gif$|\.jpg$|\.png$|\.bmp$/i).test(src))src+="."+this.type;
			if(key==undefined||key==null || key=="")key=src.replace(/(.*\/){0,}([^\.]+).*/ig,"$2");
			
			var img=new Image();img.index=count;
			item[count]=img;item[key]=img;count++;
			img.src=src;
			return img;
		};
		
		this.path="";
		this.type="gif";
		this.item = item;
		this.count=function(){return count;}
	};

};