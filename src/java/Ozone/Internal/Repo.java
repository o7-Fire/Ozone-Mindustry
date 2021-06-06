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

package Ozone.Internal;

import Atom.File.RepoInternal;
import Atom.Utility.Encoder;
import Atom.Utility.Pool;
import Shared.WarningHandler;
import arc.graphics.Pixmap;
import arc.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Future;

public class Repo extends Atom.File.Repo implements ModuleInterfaced {
	private static Repo INSTANCE = null;
	
	public static Repo getRepo() {
		return INSTANCE;
	}
	
	@Override
	protected ArrayList<Future<URL>> parallelSearch(String s) {
		ArrayList<Future<URL>> a = super.parallelSearch(s);
		a.add(Pool.submit(() -> RepoInternal.class.getClassLoader().getResource(s)));
		a.add(Pool.submit(() -> ClassLoader.getSystemResource(s)));
		return a;
	}
	
	@Override
	public void init() throws Throwable {
		addRepo(new URL("https://raw.githubusercontent.com/o7-Fire/Mindustry-Ozone/master"));
		try {
			for (String s : readString("src/repos.txt").split("\n"))
				if (!s.startsWith("#")) addRepo(new URL(s));
		}catch (Throwable t) {
			WarningHandler.handleMindustry(t);
		}
		addRepo(new URL("https://www.o7fire.tk/ozone"));
		INSTANCE = this;
	}
	
	
	public HashMap<String, String> readMap(String path) throws IOException {
		return Encoder.parseProperty(getResource(path).openStream());
	}
	
	public ArrayList<String> readArrayString(String path) throws IOException {
		return new ArrayList<>(Arrays.asList(readString(path).split("\n")));
	}
	
	public String readString(String path) throws IOException {
		return Encoder.readString(getResource(path).openStream());
	}
	
	public Pixmap getPixmap(String path) {
		URL u = getResource(path);
		Pixmap p = null;
		try {
			p = new Pixmap(Encoder.readAllBytes(u.openStream()));
		}catch (Throwable a) {
			Log.debug("Failed to load @ cause: @", path, a.toString());
		}
		return p;
	}
}
