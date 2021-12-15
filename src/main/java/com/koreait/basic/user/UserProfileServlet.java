package com.koreait.basic.user;

import com.koreait.basic.FileUtils;
import com.koreait.basic.Utils;
import com.koreait.basic.dao.UserDAO;
import com.koreait.basic.user.model.UserEntity;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req);
        UserEntity entity = new UserEntity();
        entity.setIuser(loginUserPk);

        req.setAttribute("data", UserDAO.selUser(entity));

        String title = "프로필";
        req.setAttribute("subPage", "/user/profile");//열어야할 jsp 파일명
        Utils.displayView(title, "/user/myPage", req, res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req);//폴더만들때 필요한 PK값 주기위해서 가져온 Pk값
        int fieMaxSize = 10_485_760; //1024 * 1024 * 10 (10mb), 이미지 최대사이즈

        ServletContext application = req.getServletContext();//ServletContext 타입의 application 객체
        String targetPath = application.getRealPath("/res/img/profile/" + loginUserPk);
        //빈칸시 톰켓에서 돌아가는 프로젝트 root 경로값을 준다. (톰켓이 돌아가는 root 폴더), 값을 주는거지 만들어 주는 코드는 아님
        File targetFolder = new File(targetPath);//폴더를 만들어주는 역할의 객체

        if(targetFolder.exists()) {
            FileUtils.delFolderFiles(targetPath, false);
        } else {
            targetFolder.mkdirs();//디렉토리삭제
        }
        //파일 업로드
        MultipartRequest mr =
                new MultipartRequest(req, targetPath, fieMaxSize, "UTF-8", new DefaultFileRenamePolicy());
        //new DefaultFileRenamePolicy() -> 중복이 안되게 이름 바꿔주는 역할(아무의미없는 숫자(1,2,...) 붙혀준다.)

        Enumeration enumFiles = mr.getFileNames();//ResultSet 이랑 비슷
        String changedFileNm = mr.getFilesystemName("profileImg");//변경된 파일명

        UserEntity entity = new UserEntity();

        entity.setIuser(loginUserPk);
        entity.setProfileImg(changedFileNm);

        int result = UserDAO.updUser(entity);

        switch (result) {
            case 1:
                res.sendRedirect("/user/profile");
                break;
            default:
                res.sendRedirect("/user/profile");
                req.setAttribute("err", "수정을 실패하였습니다.");
                break;
        }
    }
}
