package cn.itfh.business.entity;

/***
 *
 *  @className: Test4Entity
 *  @author: fh
 *  @date: 2020/8/11
 *  @version : V1.0
 */
public class Test4Entity {
    private int id;
    private String pd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    @Override
    public String toString() {
        return "Test4Entity{" +
                "id=" + id +
                ", pd='" + pd + '\'' +
                '}';
    }
}
