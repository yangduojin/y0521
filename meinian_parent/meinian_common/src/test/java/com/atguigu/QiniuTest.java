package com.atguigu;

import com.atguigu.utils.QiniuUtils;
import org.junit.Test;

public class QiniuTest {
    
    @Test
    public void testQiniu(){
        String path = "D:\\guigufile\\worldMap.jpg";
        String filename = "new worldMap2";
        QiniuUtils.upload2Qiniu(path,filename);
    }

    @Test
    public void testDelQiniu(){
        String filename = "new worldMap";
        QiniuUtils.deleteFileFromQiniu(filename);
    }
}
