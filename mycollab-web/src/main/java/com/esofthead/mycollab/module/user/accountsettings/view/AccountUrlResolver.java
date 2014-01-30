/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.user.accountsettings.view;

import com.esofthead.mycollab.eventmanager.EventBus;
import com.esofthead.mycollab.module.user.accountsettings.team.view.RoleUrlResolver;
import com.esofthead.mycollab.module.user.accountsettings.team.view.UserUrlResolver;
import com.esofthead.mycollab.module.user.accountsettings.view.events.AccountBillingEvent;
import com.esofthead.mycollab.module.user.accountsettings.view.events.ProfileEvent;
import com.esofthead.mycollab.shell.events.ShellEvent;
import com.esofthead.mycollab.vaadin.desktop.ui.ModuleHelper;
import com.esofthead.mycollab.vaadin.desktop.ui.UrlResolver;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class AccountUrlResolver extends UrlResolver {
	public UrlResolver build() {
		this.addSubResolver("preview", new ReadUrlResolver());
		this.addSubResolver("billing", new BillingUrlResolver());
		this.addSubResolver("user", new UserUrlResolver());
		this.addSubResolver("role", new RoleUrlResolver());
		return this;
	}

	@Override
	public void handle(String... params) {
		if (!ModuleHelper.isCurrentAccountModule()) {
			EventBus.getInstance().fireEvent(
					new ShellEvent.GotoUserAccountModule(this, params));
		} else {
			super.handle(params);
		}
	}

	@Override
	protected void defaultPageErrorHandler() {
		EventBus.getInstance().fireEvent(
				new ProfileEvent.GotoProfileView(this, null));

	}

	private static class ReadUrlResolver extends AccountUrlResolver {
		protected void handlePage(String... params) {
			EventBus.getInstance().fireEvent(
					new ProfileEvent.GotoProfileView(this, null));
		}
	}

	private static class BillingUrlResolver extends AccountUrlResolver {
		protected void handlePage(String... params) {
			EventBus.getInstance().fireEvent(
					new AccountBillingEvent.GotoSummary(this, null));
		}
	}
}