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

package Ozone.Commands.Class;

import mindustry.gen.Icon;
import org.jetbrains.annotations.Nullable;

public class PowerNode extends CommandsClass {

	public boolean all = false;
	
	public boolean disconnect = false;
	
	public boolean connect = false;
	
	public PowerNode() {
		description = "Manage Power nodes";
		icon = Icon.power;
	}
	
	@Override
	public void run() throws Exception {
	
	}
	
	@Nullable
	@Override
	public CommandsArgument getArgumentClass() {
		return null;
	}
}
