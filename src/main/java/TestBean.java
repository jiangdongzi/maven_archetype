public class TestBean {
    private String name;
    private Integer id;

    public TestBean(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public TestBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
