package AbstractViewForActivity;

import android.content.Context;
import android.view.View;

//모든 뷰는 이 클래스를 상속 함으로써 공통적인 규약을 지킨다고 보면된다.
public abstract class AbstractViewForActivity {
	private View root;
	private Context context;

	public AbstractViewForActivity(Context context) {
		this.context = context;
		this.root = inflate(); // 실질적인 뷰를 생성해내는 부분
		initViews(); 
		setEvent();
	}

	public View findViewById(int aResourceId) {
		return getRoot().findViewById(aResourceId);
	}

	abstract protected View inflate(); // inflate

	abstract protected void initViews(); // findByViewId

	abstract protected void setEvent();  // click event etc...

	public View getRoot() { // inflate된 뷰를 가지고 온다.
		return this.root;
	}

	public Context getContext() { // context가 필요할 때 사용
		return this.context;
	}
}