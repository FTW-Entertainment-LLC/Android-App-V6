package tv.anime.ftw.adapter;

/**
 * Drawer Item
 */
public class SettingsItem {
    private int icon;
    private String title;
    private String subTitle;
    private boolean isMenu;
    private int action;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isMenu() {
        return isMenu;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int screen) {
        this.action = screen;
    }
}