package com.bj.jwt;
abstract class CPunish {
	//��Ա������Ϣ
		public String sname;//����	
		public String sphone;//�绰
		public String sarchivesnum;//�������
		public String sidentitycard;//���֤��
		public String sdrivetype;//׼�ݳ���
		public String speopletype;//��Ա����
		public String slicence;//��֤����
		//����������Ϣ
		public String scarnum; //���ƺ�
		public String scarnumtype; //��������
		public String straffictype;//��ͨ��ʽ
		public String scartype;//��������
		//Υ����Ϣ
		public String sroadnum;//Υ���ص����
		public String sroadname;//
		public String slawcode;//Υ������
		public String slawmoney;//Υ�����
		public String slawscore;//Υ������
		public String slawarticles; //��������
		public String slawinfo;//Υ������
		public String sforcetype;//ǿ�ƴ�ʩ����
		//��Ա��Ϣ
		public String spolicenum;
		public String spolicename;
		public String spoliceteam;//��Ա���ڴ��
		
		public String spunishdate;//����ʱ��
		public String sillegal;//Υ��ʱ�� 
		public String steam,sgoverment;  //������λ
	public abstract String printpunish(); //��ӡ
	public abstract void SavepunishDB();
	public abstract boolean UploadpunishDB();
}
