package min.smboard.hander;

public class HandlerAdapter {
	private boolean redirect = false;
	private String path = null;

	public boolean isRedirect( ) {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public String getPath( ) {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString( ) {
		return "HandlerAdapter [redirect=" + redirect + ", path=" + path + "]";
	}

}
