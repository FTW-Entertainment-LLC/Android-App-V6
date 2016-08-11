package tv.animeftw.app.events;

public class PopupMenuItemClickedEvent {
    private final int menuItemId;
    private final int menuId;

    public PopupMenuItemClickedEvent(int menuItemId, int menuId) {
        this.menuItemId = menuItemId;
        this.menuId = menuId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public int getMenuId() {
        return menuId;
    }
}
