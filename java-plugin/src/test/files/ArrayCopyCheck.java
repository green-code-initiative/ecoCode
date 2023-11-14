/*
 * ecoCode - Java language - Provides rules to reduce the environmental footprint of your Java programs
 * Copyright © 2023 Green Code Initiative (https://www.ecocode.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.greencodeinitiative.java.checks;

import java.util.Arrays;

class TestClass {

	public boolean[]  copyArrayOK() {
		final boolean[] src = new boolean[5];

		// Copy with clone
		boolean[] dest = src.clone();

		// Copy with System.arraycopy()
		System.arraycopy(src, 0, dest, 0, src.length);

		// Copy with Arrays.copyOf()
		return Arrays.copyOf(src, src.length);
	}

	public String nonRegression() {
		final int len = 5;
		boolean[] dest = new boolean[len];

		// Simple assignation
		for (int i = 0; i < len; ++i) {
			dest[i] = true;
		}

		// Edit same array
		for (int i = 0; i < len-1; ++i) {
			dest[i] = dest[i+1];
		}

		// Objects assignations
		String a = null;
		if (dest.length > 0) {
			String b = "Sample Value";
			for (int i = 0; i < len; ++i) {
				a = b;
			}
		}

		return a;
	}

	public boolean[] copyWithForLoop() {
		final int len = 5;
		final boolean[] src = new boolean[len];
		boolean[] dest = new boolean[len];

		// Simple copy
		for (int i = 0; i < len; ++i) {
			dest[i] = src[i];
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested conditions
		for (int i = 0; i < len; ++i) {
			if (i + 2 < len) {
				dest[i] = src[i + 2];
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		return dest;
	}

	public void copyWithForLoop2() {
		final int len = 5;
		final boolean[] src = new boolean[len];
		boolean[] dest = new boolean[len];

		// Copy with nested ELSE conditions
		for (int i = 0; i < len; ++i) {
			if (i + 2 >= len) {
				++i;
			} else {
				dest[i] = src[i + 2];
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with more nested conditions
		for (int i = 0; i < len; ++i) {
			if (i + 2 < len) {
				if (dest != null) {
					if (src != null) {
						if (i > 1 && i + 2 < src.length) {
							dest[i] = src[i + 2];
						}
					}
				}
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

	}

	public void copyWithForLoop3() {
		final int len = 5;
		final boolean[] src = new boolean[len];
		boolean[] dest = new boolean[len];

		// Copy nested by try/catch
		for (int i = 0; i < len; ++i) {
			try {
				dest[i] = src[i];
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch and if
		for (int i = 0; i < len; ++i) {
			try {
				if (dest != null) {
					dest[i] = src[i];
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment
	}

	public void copyWithForLoop4() {
		final int len = 5;
		final boolean[] src = new boolean[len];
		boolean[] dest = new boolean[len];

		// Copy nested by try/catch in catch
		for (int i = 0; i < len; ++i) {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				if(dest != null) {
					dest[i] = src[i];
				}
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch in finally
		for (int i = 0; i < len; ++i) {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				e.printStackTrace();
			} finally {
				dest[i] = src[i];
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Array transformation
		for (int i = 0; i < len; ++i) {
			dest[i] = transform(src[i]);
		}
	}

	public void copyWithForEachLoop() {
		final int len = 5;
		final boolean[] src = new boolean[len];
		boolean[] dest = new boolean[len];

		// Simple copy by foreach
		int i = -1;
		for (boolean b : src) {
			dest[++i] = b;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested conditions by foreach
		i = -1;
		for (boolean b : src) {
			if(b) {
				dest[++i] = b;
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested ELSE conditions by foreach
		i = -1;
		for (boolean b : src) {
			if(i + 2 >= len) {
				++i;
			} else {
				dest[++i] = b;
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with more nested conditions
		i = -1;
		for (boolean b : src) {
			if(i + 2 < len) {
				if(dest != null) {
					if(src != null) {
						if(i > 1 && i + 2 < src.length) {
							dest[++i] = b;
						}
					}
				}
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch
		i = -1;
		for (boolean b : src) {
			try {
				dest[++i] = b;
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch and if
		i = -1;
		for (boolean b : src) {
			try {
				if(dest != null) {
					dest[++i] = b;
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch in catch
		i = -1;
		for (boolean b : src) {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				if(dest != null) {
					dest[++i] = b;
				}
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch in finally
		i = -1;
		for (boolean b : src) {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				e.printStackTrace();
			} finally {
				dest[++i] = b;
			}
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Array transformation
		i = -1;
		for (boolean b : src) {
			dest[++i] = transform(b);
		}

		// Simple copy
		i = 0;
		for (boolean b : src) {
			dest[i] = src[i];
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested conditions
		i = 0;
		for (boolean b : src) {
			if(b) {
				dest[i] = src[i];
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested ELSE conditions
		i = 0;
		for (boolean b : src) {
			if(i + 2 >= len) {
				++i;
			} else {
				dest[i] = src[i + 2];
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with more nested conditions
		i = 0;
		for (boolean b : src) {
			if(i + 2 < len) {
				if(dest != null) {
					if(src != null) {
						if(i > 1 && i + 2 < src.length) {
							dest[i] = src[i + 2];
						}
					}
				}
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch
		i = 0;
		for (boolean b : src) {
			try {
				dest[i] = src[i];
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch and if
		i = 0;
		for (boolean b : src) {
			try {
				if(dest != null) {
					dest[i] = src[i];
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch in catch
		i = 0;
		for (boolean b : src) {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				if(dest != null) {
					dest[i] = src[i];
				}
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch in finally
		i = 0;
		for (boolean b : src) {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				e.printStackTrace();
			} finally {
				dest[i] = src[i];
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Array transformation
		i = 0;
		for (boolean b : src) {
			dest[i] = transform(src[i]);
			++i;
		}
	}

	public void copyWithWhileLoop() {
		final int len = 5;
		final boolean[] src = new boolean[len];
		boolean[] dest = new boolean[len];

		// Simple copy
		int i = 0;
		while (i < len) {
			dest[i] = src[i];
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested conditions
		i = 0;
		while (i < len) {
			if(i + 2 < len) {
				dest[i] = src[i + 2];
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested ELSE conditions
		i = 0;
		while (i < len) {
			if(i + 2 >= len) {
				++i;
			} else {
				dest[i] = src[i + 2];
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with more nested conditions
		i = 0;
		while (i < len) {
			if(i + 2 < len) {
				if(dest != null) {
					if(src != null) {
						if(i > 1 && i + 2 < src.length) {
							dest[i] = src[i + 2];
						}
					}
				}
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch and if
		i = 0;
		while (i < len) {
			try {
				if(dest != null) {
					dest[i] = src[i];
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch in catch
		i = 0;
		while (i < len) {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				if(dest != null) {
					dest[i] = src[i];
				}
			}
			++i;
		} // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Array transformation
		i = 0;
		while (i < len) {
			dest[i] = transform(src[i]);
			++i;
		}
	}

	public void copyWithDoWhileLoop() {
		final int len = 5;
		final boolean[] src = new boolean[len];
		boolean[] dest = new boolean[len];

		// Simple copy
		int i = 0;
		do {
			dest[i] = src[i];
			++i;
		} while (i < len); // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested conditions
		i = 0;
		do {
			if(i + 2 < len) {
				dest[i] = src[i + 2];
			}
			++i;
		} while (i < len); // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with nested ELSE conditions
		i = 0;
		do {
			if(i + 2 >= len) {
				++i;
			} else {
				dest[i] = src[i + 2];
			}
			++i;
		} while (i < len); // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy with more nested conditions
		i = 0;
		do {
			if(i + 2 < len) {
				if(dest != null) {
					if(src != null) {
						if(i > 1 && i + 2 < src.length) {
							dest[i] = src[i + 2];
						}
					}
				}
			}
			++i;
		} while (i < len); // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch and if
		i = 0;
		do {
			try {
				if(dest != null) {
					dest[i] = src[i];
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			++i;
		} while (i < len); // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Copy nested by try/catch in catch
		i = 0;
		do {
			try {
				dest.toString();
			} catch (RuntimeException e) {
				if(dest != null) {
					dest[i] = src[i];
				}
			}
			++i;
		} while (i < len); // Noncompliant {{Use System.arraycopy to copy arrays}} // NOSONAR to remove issue on comment

		// Array transformation
		i = 0;
		do {
			dest[i] = transform(src[i]);
			++i;
		} while (i < len);
	}

	private boolean transform(boolean a) {
		return !a;
	}

}