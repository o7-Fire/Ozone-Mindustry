/*******************************************************************************
 * Copyright 2021 Itzbenz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

/* o7 Inc 2021 Copyright

  Licensed under the o7 Inc License, Version 1.0.1, ("the License");
  You may use this file but only with the License. You may obtain a
  copy of the License at
  
  https://github.com/o7-Fire/Mindustry-Ozone/Licenses
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the license for the specific language governing permissions and
  limitations under the License.
*/

package Ozone.Experimental.Payload;

import Ozone.Experimental.AttackDiagram;
import mindustry.Vars;
import mindustry.ui.dialogs.JoinDialog;

import static mindustry.Vars.logic;
import static mindustry.Vars.net;

public class BasicConnectDiagram extends AttackDiagram {
	JoinDialog.Server target;
	boolean randomName;
	String name;
	
	public BasicConnectDiagram(JoinDialog.Server server, boolean randomName) {
		target = server;
		this.randomName = randomName;
		if (randomName) onTaskCompleted(s -> Vars.player.name = name);
	}
	
	
	void run() {
		Vars.netClient.disconnectQuietly();
		if (randomName) name = Vars.player.name;
		logic.reset();
		net.reset();
		Vars.netClient.beginConnecting();
		net.connect(target.ip, target.port, () -> { });
		
	}
	
	@Override
	public boolean isCompleted() {
		return net.active();
	}
}
