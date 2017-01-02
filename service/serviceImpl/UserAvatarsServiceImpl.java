package cn.edu.bjtu.weibo.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.dao.ImgDAO;
import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.UserAvatarsService;

@Service("userAvatarsService")
public class UserAvatarsServiceImpl implements UserAvatarsService{
	
	@Autowired
	UserDAO userDao;
	@Autowired
	ImgDAO imgDao;
	@Autowired
	PictureServiceImpl picImpl;

	@Override
	public List<Picture> getUserAvatarList(String userId, int pageIndex, int numberPerPage) {
		// TODO Auto-generated method stub	
		List<String> list = userDao.getUserAvatars(userId, pageIndex, numberPerPage);
		
		List<Picture>  newList = new ArrayList<Picture>();
		
		for(int i = 0 ; i < list.size() ; i++){
			
			String id = list.get(i);
			Picture pic = picImpl.getPicture(id);
			
			newList.add(pic);
		}
		
		return newList;
	}

	@Override
	public boolean uploadUserAvatar(String userId, MultipartFile multipartFile) {
		// TODO Auto-generated method stub	
		List<String> list = picImpl.uploadPicture(multipartFile);
		
		if(list.size()==1){
			String picId = list.get(0);
			
			if(picId!=null){
				return userDao.insertUserAvatar(userId, picId);
			}
		}
		
		return false;
	}

}
