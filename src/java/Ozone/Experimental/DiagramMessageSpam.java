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

package Ozone.Experimental;

import Ozone.Commands.TaskInterface;
import Ozone.Manifest;
import arc.Core;
import mindustry.Vars;

import java.util.function.Consumer;

public class DiagramMessageSpam implements Experimental {
	public static void execute(Consumer<ConnectDiagram.ConnectDiagramProvider> c) {
		int thread = 4;
		try {thread = Runtime.getRuntime().availableProcessors() * 2;}catch (Throwable ignored) {}
		int finalThread = thread;
		String prefixS = DiagramMessageSpam.class.getName();
		//holy shit get to outside
		Vars.ui.showTextInput("Enter server ip", "", Core.settings.getString(prefixS + ".ip", "mindustry.io"), s1 -> {
				Vars.ui.showTextInput("Enter server port", "", 6, Core.settings.getString(prefixS + ".port", Vars.port + ""), true, s2 -> {
					//what the fuck, get some fresh air
					Vars.ui.showTextInput("Max Thread", "careful mate", 2, finalThread + "", true, s3 -> {
							Vars.ui.showTextInput("Surprise ?", "Send spam message", "randomizer", s5 -> {
								Vars.ui.showTextInput("Enable Join Message", "true/false", Core.settings.getBool(prefixS + ".join", true) + "", s6 -> {
									Vars.ui.showConfirm("Confirm", "Are you sure ?", () -> {
										try {
											Core.settings.put(prefixS + ".ip", s1);
											Core.settings.put(prefixS + ".port", s2);
											boolean joinMessage = Boolean.parseBoolean(s6);
											Core.settings.put(DiagramMessageSpam.class.getName() + ".join", joinMessage);
											String s4 = s5;
											if (s4.equals("randomizer")) s4 = "";
											//holy shit ben, couldnt care less
											if (s4.toUpperCase().contains("o7".toUpperCase()) || s4.toUpperCase().contains("ozone".toUpperCase()))
												throw new IllegalArgumentException("jews dont use our fucking name when doing this");
									TaskInterface.addTask(new ConnectDiagram(s1, Integer.parseInt(s2), s4, Integer.parseInt(s3), joinMessage));
									Manifest.toast("See Task List");
								}catch (Throwable t) {
									Vars.ui.showException(t);
								}
							});
						});
					});
				});
			});
		});
	}
	
	@Override
	public void run() {
		execute(null);
	}
}
