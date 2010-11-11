package mm.smy.bicq.server.message ;

/**
* TextMessage�����ݿ��ȡ
* 
* 
* @author XF
* @date 2003-11-21
* 
*/

import mm.smy.bicq.server.db.ReadonlyStatement ;

import java.sql.SQLException ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;

import java.util.Vector ;

public class TextDBQuery{
	
	private ReadonlyStatement ro = null ;
	private PreparedStatement pstm = null ;
	private ResultSet rs = null ;
	
	public TextDBQuery(){
		
	}
	
	/**
	* �����û�number���������� ���͸� ���û���TextMessage������Ϣ��
	* ���Զ�ε��øú�����ÿ�����²쿴���ݿ⡣ʹ��PreparedStatement����ÿ�ε��ú󲻹ر����ӡ�
	* �����ε��ÿ������Ч�ʣ�����ע���������ʱ����close()�����ͷ�jdbc��Դ��
	* @param to Ҫ������ ���͸� ���û���BICQ��
	* @return Vector�����������ServerTextMessage��Ϣ���ϡ�
	*/
	public Vector selectByNumber(int to) throws SQLException{
		if(ro == null){
			ro = new ReadonlyStatement("text") ;	
		}
		if(pstm == null)
			pstm = ro.getPreparedStatement("select * from textmessage where tonumber = ?") ;
		else
			pstm.clearParameters() ;
		pstm.setInt(1,to) ;
		
		rs = pstm.executeQuery() ;
		
		Vector v = new Vector() ;
		ServerTextMessage tm = null ;
		while(rs.next()){
			tm = new ServerTextMessage() ;
			tm.setByteContent(rs.getBytes("content")) ;
			tm.setFrom(rs.getInt("fromnumber")) ;
			tm.setTo(rs.getInt("tonumber")) ;
			v.add(tm) ;
		}
		
		rs.close() ;
		
		return v ;
	}
	
	public void close(){
		if(ro != null)
			ro.close() ;
		if(pstm != null){
			try{
				pstm.close() ;
			}catch(Exception e){
			}
		}
		return ;
	}

		
	
	
	
}