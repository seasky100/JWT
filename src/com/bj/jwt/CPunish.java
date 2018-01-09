package com.bj.jwt;
abstract class CPunish {
	//人员基本信息
		public String sname;//姓名	
		public String sphone;//电话
		public String sarchivesnum;//档案编号
		public String sidentitycard;//身份证号
		public String sdrivetype;//准驾车型
		public String speopletype;//人员种类
		public String slicence;//发证机关
		//车辆基本信息
		public String scarnum; //车牌号
		public String scarnumtype; //号牌种类
		public String straffictype;//交通方式
		public String scartype;//车辆分类
		//违法信息
		public String sroadnum;//违法地点代码
		public String sroadname;//
		public String slawcode;//违法代码
		public String slawmoney;//违法金额
		public String slawscore;//违法积分
		public String slawarticles; //法律条文
		public String slawinfo;//违法内容
		public String sforcetype;//强制措施类型
		//警员信息
		public String spolicenum;
		public String spolicename;
		public String spoliceteam;//警员所在大队
		
		public String spunishdate;//处罚时间
		public String sillegal;//违法时间 
		public String steam,sgoverment;  //行政单位
	public abstract String printpunish(); //打印
	public abstract void SavepunishDB();
	public abstract boolean UploadpunishDB();
}
