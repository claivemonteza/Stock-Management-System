package sms.report;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Iframe;

//TODO Create documentation

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReportPresenterController extends GenericForwardComposer <Component> {

	private static final long serialVersionUID = 6505108298348437813L;

	@Wire
	private Iframe iframe;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Media media = (Media) SessionHelper.takeObject("reportToGenerate");
		iframe.setContent(media);
	}
}
